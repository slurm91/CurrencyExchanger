package by.vzhilko.currencyexchanger.feature.currencyexchange.data.dto

import com.google.gson.annotations.SerializedName

class CurrencyExchangeDto(
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String,
    @SerializedName("rates") val currencyRates: CurrencyRatesDto
)