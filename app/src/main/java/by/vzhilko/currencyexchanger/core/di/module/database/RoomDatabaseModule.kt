package by.vzhilko.currencyexchanger.core.di.module.database

import android.content.Context
import androidx.room.Room
import by.vzhilko.currencyexchanger.core.datasource.database.config.IDatabaseConfig
import by.vzhilko.currencyexchanger.core.datasource.database.room.AppRoomDatabase
import by.vzhilko.currencyexchanger.core.di.annotation.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class RoomDatabaseModule {

    @AppScope
    @Provides
    fun providerAppRoomDatabase(
        context: Context,
        config: IDatabaseConfig
    ): AppRoomDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppRoomDatabase::class.java,
            name = config.databaseName
        ).build()
    }

}