package by.vzhilko.currencyexchanger.core.di.component

import android.content.Context
import by.vzhilko.currencyexchanger.core.di.annotation.scope.AppScope
import by.vzhilko.currencyexchanger.core.di.module.AppModule
import by.vzhilko.currencyexchanger.feature.currencyexchange.di.CurrencyExchangeFragmentComponent
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    fun currencyExchangeFragmentComponentBuilder(): CurrencyExchangeFragmentComponent.Builder

}