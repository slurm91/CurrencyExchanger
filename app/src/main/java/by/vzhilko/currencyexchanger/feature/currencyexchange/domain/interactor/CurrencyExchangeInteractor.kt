package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.interactor

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.*
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.repository.ICurrencyExchangeRepository
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.state.CurrencyExchangeState
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util.SellCurrencyComparator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class CurrencyExchangeInteractor(
    private val repository: ICurrencyExchangeRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun getRates(): CurrencyExchangeState<CurrencyExchangeData> = withContext(ioDispatcher) {
        try {
            val data: CurrencyExchangeData = repository.getRates()
            CurrencyExchangeState.Success(data)
        } catch (error: Throwable) {
            CurrencyExchangeState.Error(error)
        }
    }

    suspend fun getBalancesList(currencyExchangeData: CurrencyExchangeData?): CurrencyExchangeState<List<BalanceData>> {
        if (currencyExchangeData == null) {
           return CurrencyExchangeState.Error(
               Exception(repository.getErrorBalancesListLoadingDefaultError())
           )
        }

        return withContext(ioDispatcher) {
            try {
                val data: List<BalanceData> = repository.getBalancesList(currencyExchangeData)
                CurrencyExchangeState.Success(data)
            } catch (error: Throwable) {
                CurrencyExchangeState.Error(error)
            }
        }
    }

    suspend fun getSellCurrenciesList(currencyExchangeData: CurrencyExchangeData): CurrencyExchangeState<List<CurrencyData>> {
        return withContext(defaultDispatcher) {
            //imagine that currencies list which we want to sell is equaled to currencies list which we want to buy.
            val list: List<CurrencyData> = currencyExchangeData.currencyRatesList
                .map { rates -> rates.currency }
                .sortedWith(SellCurrencyComparator(currencyExchangeData.baseCurrency))
            CurrencyExchangeState.Success(list)
        }
    }

    suspend fun getReceiveCurrenciesList(currencyExchangeData: CurrencyExchangeData): CurrencyExchangeState<List<CurrencyData>> {
        return withContext(defaultDispatcher) {
            val list: List<CurrencyData> = currencyExchangeData.currencyRatesList.map { rates -> rates.currency }
            CurrencyExchangeState.Success(list)
        }
    }

    suspend fun exchangeCurrency(
        currencyExchangeData: CurrencyExchangeData?,
        sellCurrencyBalance: BigDecimal?,
        sellCurrency: CurrencyData?,
        receiveCurrency: CurrencyData?
    ): CurrencyExchangeState<BigDecimal> {
        if (currencyExchangeData == null
            || sellCurrencyBalance == null
            || sellCurrency == null
            || receiveCurrency == null
        ) {
            return CurrencyExchangeState.Error(
                Exception(repository.getErrorCurrencyExchangeCalculationDefaultMessage())
            )
        }

        return withContext(defaultDispatcher) {
            val baseCurrency: CurrencyData = currencyExchangeData.baseCurrency
            val ratesList: List<CurrencyRatesData> = currencyExchangeData.currencyRatesList
            val result: BigDecimal
            when (sellCurrency) {
                baseCurrency -> {
                    val receiveCurrencyRate: BigDecimal? = ratesList.find { data -> data.currency == receiveCurrency }?.rate
                    if (receiveCurrencyRate != null) {
                        result = sellCurrencyBalance.multiply(receiveCurrencyRate)
                        CurrencyExchangeState.Success(result)
                    } else {
                        CurrencyExchangeState.Error(
                            Exception(repository.getErrorCurrencyExchangeCalculationDefaultMessage())
                        )
                    }
                }
                else -> {
                    val sellCurrencyRate: BigDecimal? = ratesList.find { data -> data.currency == sellCurrency }?.rate
                    val receiveCurrencyRate: BigDecimal? = ratesList.find { data -> data.currency == receiveCurrency }?.rate
                    if (sellCurrencyRate != null && receiveCurrencyRate != null) {
                        result = sellCurrencyBalance
                            .divide(sellCurrencyRate, MathContext(6, RoundingMode.HALF_UP))
                            .multiply(receiveCurrencyRate)
                        CurrencyExchangeState.Success(result)
                    } else {
                        CurrencyExchangeState.Error(
                            Exception(repository.getErrorCurrencyExchangeCalculationDefaultMessage())
                        )
                    }
                }
            }
        }
    }

    suspend fun refreshBalance(
        balancesList: List<BalanceData>?,
        sellCurrencyBalance: BigDecimal?,
        receiveCurrencyBalance: BigDecimal?,
        sellCurrency: CurrencyData?,
        receiveCurrency: CurrencyData?
    ): CurrencyExchangeState<String> {
        if (balancesList == null
            || sellCurrencyBalance == null
            || receiveCurrencyBalance == null
            || sellCurrency == null
            || receiveCurrency == null
        ) {
            return CurrencyExchangeState.Error(
                Exception(repository.getErrorBalanceRefreshDefaultMessage())
            )
        }

        return withContext(ioDispatcher) {
            when (sellCurrency) {
                receiveCurrency -> {
                    CurrencyExchangeState.Error(
                        Exception(repository.getErrorCurrencyExchangeMessageIfCurrenciesAreEqualed())
                    )
                }
                else -> {
                    val sellCurrencyBalanceData: BalanceData? = balancesList.find { it.currency == sellCurrency }
                    val receiveCurrencyBalanceData: BalanceData? = balancesList.find { it.currency == receiveCurrency }
                    if (sellCurrencyBalanceData != null && receiveCurrencyBalanceData != null) {
                        val commissionData: CommissionData = repository.getCommission(CommissionData.Type.FIRST_FIVE_FREE)
                        val commission: BigDecimal = calculateCommission(commissionData, sellCurrencyBalance)

                        val newSellCurrencyBalance: BigDecimal = sellCurrencyBalanceData.balance.subtract(sellCurrencyBalance).subtract(commission)
                        val newReceiveCurrencyBalance: BigDecimal = receiveCurrencyBalanceData.balance.add(receiveCurrencyBalance)
                        if (newSellCurrencyBalance.compareTo(BigDecimal.ZERO) == -1) {
                            CurrencyExchangeState.Error(
                                Exception(repository.getErrorBalanceRefreshDefaultMessage())
                            )
                        } else {
                            repository.refreshBalance(
                                sellBalance = newSellCurrencyBalance,
                                sellCurrency = sellCurrency,
                                receiveBalance = newReceiveCurrencyBalance,
                                receiveCurrency = receiveCurrency
                            )

                            if (commissionData.attemptsCount < commissionData.maxAttemptsCount) {
                                repository.refreshCommission(
                                    attemptsCount = commissionData.attemptsCount + 1,
                                    type = commissionData.type
                                )
                            }

                            val message: String = repository.getSuccessfulCurrencyExchangeMessage(
                                sellBalance = sellCurrencyBalance,
                                sellCurrency = sellCurrency,
                                receiveBalance = receiveCurrencyBalance,
                                receiveCurrency = receiveCurrency,
                                commission = commission
                            )

                            CurrencyExchangeState.Success(message)
                        }
                    } else {
                        CurrencyExchangeState.Error(
                            Exception(repository.getErrorBalanceRefreshDefaultMessage())
                        )
                    }
                }
            }
        }
    }

    private fun calculateCommission(data: CommissionData, sellCurrencyBalance: BigDecimal): BigDecimal {
        return if (data.attemptsCount < data.maxAttemptsCount) {
            BigDecimal.ZERO
        } else {
            sellCurrencyBalance * BigDecimal("0.007")
        }
    }

}