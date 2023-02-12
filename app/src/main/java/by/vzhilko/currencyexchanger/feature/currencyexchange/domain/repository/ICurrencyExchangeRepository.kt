package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.repository

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CommissionData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyExchangeData
import java.math.BigDecimal

interface ICurrencyExchangeRepository {

    suspend fun getRates(): CurrencyExchangeData

    suspend fun getBalancesList(currencyExchangeData: CurrencyExchangeData): List<BalanceData>

    suspend fun refreshBalance(
        sellBalance: BigDecimal,
        sellCurrency: CurrencyData,
        receiveBalance: BigDecimal,
        receiveCurrency: CurrencyData
    )

    suspend fun getCommission(type: CommissionData.Type): CommissionData

    suspend fun refreshCommission(attemptsCount: Int, type: CommissionData.Type)

    fun getSuccessfulCurrencyExchangeMessage(
        sellBalance: BigDecimal,
        sellCurrency: CurrencyData,
        receiveBalance: BigDecimal,
        receiveCurrency: CurrencyData,
        commission: BigDecimal
    ): String

    fun getErrorCurrencyExchangeMessageIfCurrenciesAreEqualed(): String

    fun getErrorCurrencyExchangeCalculationDefaultMessage(): String

    fun getErrorBalanceRefreshDefaultMessage(): String

    fun getErrorBalancesListLoadingDefaultError(): String

}