package by.vzhilko.currencyexchanger.core.di.module

import by.vzhilko.currencyexchanger.core.di.module.coroutine.CoroutinesModule
import by.vzhilko.currencyexchanger.core.di.module.database.DatabaseModule
import by.vzhilko.currencyexchanger.core.di.module.network.NetworkModule
import dagger.Module

@Module(
    includes = [
        CoroutinesModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface AppModule