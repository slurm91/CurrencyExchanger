package by.vzhilko.currencyexchanger.feature.currencyexchange.data.mapper

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import javax.inject.Inject

class CurrencyDataMapper @Inject constructor() {

    fun map(data: String): CurrencyData {
        return CurrencyData(data)
    }

}