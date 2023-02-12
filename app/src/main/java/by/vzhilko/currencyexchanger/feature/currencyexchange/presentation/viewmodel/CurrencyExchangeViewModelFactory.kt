package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.interactor.CurrencyExchangeInteractor

class CurrencyExchangeViewModelFactory(
    private val interactor: CurrencyExchangeInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyExchangeViewModel(interactor) as T
    }

}