package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.state

sealed class CurrencyExchangeState<out T : Any> {
    class Success<out T : Any>(val value: T) : CurrencyExchangeState<T>()
    class Error(val error: Throwable): CurrencyExchangeState<Nothing>()
    object Loading : CurrencyExchangeState<Nothing>()
    object NoState : CurrencyExchangeState<Nothing>()
}