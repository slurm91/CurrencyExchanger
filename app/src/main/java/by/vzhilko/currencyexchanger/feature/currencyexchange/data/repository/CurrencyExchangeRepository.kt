package by.vzhilko.currencyexchanger.feature.currencyexchange.data.repository

import android.content.Context
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.BalanceDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.api.CurrencyExchangeServiceApi
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.CommissionDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dto.CurrencyExchangeDto
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BalanceEntity
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.CommissionEntity
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.BalanceDataMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.CommissionMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper.CurrencyExchangeDataMapper
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CommissionData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyExchangeData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.repository.ICurrencyExchangeRepository
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util.BalanceComparator
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util.TwoSignsDecimalFormatter
import java.math.BigDecimal

private const val BASE_CURRENCY_STARTED_BALANCE: Double = 1000.0
private const val CURRENCY_STARTED_BALANCE: Double = 0.0

class CurrencyExchangeRepository(
    private val context: Context,
    private val serviceApi: CurrencyExchangeServiceApi,
    private val balancesDao: BalanceDao,
    private val commissionDao: CommissionDao,
    private val currencyExchangeDataMapper: CurrencyExchangeDataMapper,
    private val balanceDataMapper: BalanceDataMapper,
    private val commissionMapper: CommissionMapper
) : ICurrencyExchangeRepository {

    private val decimalFormatter: TwoSignsDecimalFormatter = TwoSignsDecimalFormatter()

    override suspend fun getRates(): CurrencyExchangeData {
        val rates: CurrencyExchangeDto = serviceApi.getRates()
        return currencyExchangeDataMapper.map(rates)
    }

    override suspend fun getBalancesList(currencyExchangeData: CurrencyExchangeData): List<BalanceData> {
        val balancesCount: Long = balancesDao.getBalancesCount()
        return if (balancesCount <= 0) {
            val commissionEntity: CommissionEntity = generateStartedCommissionEntity()
            commissionDao.insertCommission(commissionEntity)
            val entitiesList: List<BalanceEntity> = generateStartedBalanceEntityList(currencyExchangeData)
            balancesDao.insertAndGetBalances(entitiesList)
                .map { balanceDataMapper.mapTo(it) }
                .sortedWith(BalanceComparator(currencyExchangeData.baseCurrency))
        } else {
            balancesDao.getBalances()
                .map { balanceDataMapper.mapTo(it) }
                .sortedWith(BalanceComparator(currencyExchangeData.baseCurrency))
        }
    }

    override suspend fun refreshBalance(
        sellBalance: BigDecimal,
        sellCurrency: CurrencyData,
        receiveBalance: BigDecimal,
        receiveCurrency: CurrencyData
    ) {
        balancesDao.updateSellAndReceiveBalances(
            sellBalance = sellBalance.toDouble(),
            sellCurrency = sellCurrency.name,
            receiveBalance = receiveBalance.toDouble(),
            receiveCurrency = receiveCurrency.name
        )
    }

    override suspend fun getCommission(type: CommissionData.Type): CommissionData {
        val entity: CommissionEntity = commissionDao.getCommission(type.title)

        return commissionMapper.mapTo(entity)
    }

    override suspend fun refreshCommission(attemptsCount: Int, type: CommissionData.Type) {
        commissionDao.updateCommission(attemptsCount, type.title)
    }

    override fun getSuccessfulCurrencyExchangeMessage(
        sellBalance: BigDecimal,
        sellCurrency: CurrencyData,
        receiveBalance: BigDecimal,
        receiveCurrency: CurrencyData,
        commission: BigDecimal
    ): String {
        return context.getString(
            R.string.successful_currency_exchange_message,
            "${decimalFormatter.format(sellBalance)} ${sellCurrency.name}",
            "${decimalFormatter.format(receiveBalance)} ${receiveCurrency.name}",
            "${decimalFormatter.format(commission)} ${sellCurrency.name}"
        )
    }

    override fun getErrorCurrencyExchangeMessageIfCurrenciesAreEqualed(): String {
        return context.getString(R.string.sell_currency_and_receive_currency_equality_error_message)
    }

    override fun getErrorCurrencyExchangeCalculationDefaultMessage(): String {
        return context.getString(R.string.currency_exchange_calculation_default_error)
    }

    override fun getErrorBalanceRefreshDefaultMessage(): String {
        return context.getString(R.string.balance_refresh_default_error)
    }

    override fun getErrorBalancesListLoadingDefaultError(): String {
        return context.getString(R.string.balances_list_loading_default_error)
    }

    private fun generateStartedBalanceEntityList(currencyExchangeData: CurrencyExchangeData): List<BalanceEntity> {
        val list: MutableList<BalanceEntity> = mutableListOf()

        currencyExchangeData.currencyRatesList.forEach { data ->
            list.add(
                BalanceEntity(
                    balance = CURRENCY_STARTED_BALANCE,
                    currency = data.currency.name
                )
            )
        }

        list.add(
            BalanceEntity(
                balance = BASE_CURRENCY_STARTED_BALANCE,
                currency = currencyExchangeData.baseCurrency.name
            )
        )

        return list
    }

    private fun generateStartedCommissionEntity(): CommissionEntity {
        return CommissionEntity(
            title = CommissionData.Type.FIRST_FIVE_FREE.title,
            attemptsCount = 0,
            maxAttemptsCount = 5
        )
    }

}