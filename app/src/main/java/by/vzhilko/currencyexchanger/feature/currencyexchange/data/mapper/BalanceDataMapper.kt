package by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper

import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BalanceEntity
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import java.math.BigDecimal
import javax.inject.Inject

class BalanceDataMapper @Inject constructor() {

    fun mapTo(data: BalanceEntity): BalanceData {
        return BalanceData(
            currency = CurrencyData(data.currency),
            balance = BigDecimal(data.balance.toString())
        )
    }

    fun mapFrom(data: BalanceData): BalanceEntity {
        return BalanceEntity(
            currency = data.currency.name,
            balance = data.balance.toDouble()
        )
    }

}