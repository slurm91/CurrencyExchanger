package by.vzhilko.currencyexchanger.core.di.module.network

import by.vzhilko.currencyexchanger.core.datasource.network.config.DefaultHttpClientConfig
import by.vzhilko.currencyexchanger.core.datasource.network.config.IHttpClientConfig
import dagger.Binds
import dagger.Module

@Module(includes = [RetrofitModule::class])
interface NetworkModule {

    @Binds
    fun bindDefaultHttpClientConfig(httpClientConfig: DefaultHttpClientConfig): IHttpClientConfig

}