package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto

import java.math.BigDecimal

data class CurrencyRatesData(
    val currency: CurrencyData,
    val rate: BigDecimal
)