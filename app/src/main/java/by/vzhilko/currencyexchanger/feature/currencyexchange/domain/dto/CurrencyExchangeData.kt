package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto

data class CurrencyExchangeData(
    val baseCurrency: CurrencyData,
    val currencyRatesList: List<CurrencyRatesData>
)