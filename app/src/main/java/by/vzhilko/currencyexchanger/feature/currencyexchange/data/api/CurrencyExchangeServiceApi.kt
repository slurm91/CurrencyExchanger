package by.vzhilko.currencyexchanger.feature.currencyexchange.data.api

import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dto.CurrencyExchangeDto
import retrofit2.http.GET

interface CurrencyExchangeServiceApi {

    @GET("currency-exchange-rates")
    suspend fun getRates(): CurrencyExchangeDto

}