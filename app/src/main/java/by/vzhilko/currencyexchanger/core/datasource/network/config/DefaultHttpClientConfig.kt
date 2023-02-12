package by.vzhilko.currencyexchanger.core.datasource.network.config

import by.vzhilko.currencyexchanger.core.di.annotation.scope.AppScope
import javax.inject.Inject

@AppScope
class DefaultHttpClientConfig @Inject constructor() : IHttpClientConfig {
    override val hostUrl: String = "https://developers.paysera.com/tasks/api/"
}