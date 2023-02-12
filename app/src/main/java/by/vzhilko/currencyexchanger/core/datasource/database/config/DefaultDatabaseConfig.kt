package by.vzhilko.currencyexchanger.core.datasource.database.config

import by.vzhilko.currencyexchanger.core.di.annotation.scope.AppScope
import javax.inject.Inject

@AppScope
class DefaultDatabaseConfig @Inject constructor() : IDatabaseConfig {
    override val databaseName: String = "currency_exchanger_database"
}