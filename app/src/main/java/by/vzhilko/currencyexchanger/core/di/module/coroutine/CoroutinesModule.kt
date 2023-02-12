package by.vzhilko.currencyexchanger.core.di.module.coroutine

import by.vzhilko.currencyexchanger.core.di.annotation.coroutine.Default
import by.vzhilko.currencyexchanger.core.di.annotation.coroutine.IO
import by.vzhilko.currencyexchanger.core.di.annotation.coroutine.Main
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class CoroutinesModule {

    @IO
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Default
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Main
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

}