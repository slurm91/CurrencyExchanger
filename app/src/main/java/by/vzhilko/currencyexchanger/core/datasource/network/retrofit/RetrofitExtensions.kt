package by.vzhilko.currencyexchanger.core.datasource.network.retrofit

import retrofit2.Retrofit

inline fun <reified T> Retrofit.createApi(): T {
    return create(T::class.java)
}