package by.vzhilko.currencyexchanger.core.di.module.database

import by.vzhilko.currencyexchanger.core.datasource.database.config.DefaultDatabaseConfig
import by.vzhilko.currencyexchanger.core.datasource.database.config.IDatabaseConfig
import dagger.Binds
import dagger.Module

@Module(includes = [RoomDatabaseModule::class])
interface DatabaseModule {

    @Binds
    fun bindDefaultDatabaseConfig(config: DefaultDatabaseConfig): IDatabaseConfig

}