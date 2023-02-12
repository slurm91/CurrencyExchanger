package by.vzhilko.currencyexchanger.feature.currencyexchange.di

import by.vzhilko.currencyexchanger.core.di.annotation.scope.FragmentScope
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.fragment.CurrencyExchangeFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CurrencyExchangeFragmentModule::class])
interface CurrencyExchangeFragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun currencyExchangeFragment(fragment: CurrencyExchangeFragment): Builder
        fun build(): CurrencyExchangeFragmentComponent
    }

    fun inject(fragment: CurrencyExchangeFragment)

}