package by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyRatesData
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyRatesDataMapper @Inject constructor(
    private val currencyDataMapper: CurrencyDataMapper
) {

    fun map(currencyName: String, rate: Double): CurrencyRatesData {
        return CurrencyRatesData(
            currency = currencyDataMapper.map(currencyName),
            rate = BigDecimal(rate.toString())
        )
    }

}