package by.vzhilko.currencyexchanger.core.datasource.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.BalanceDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao.CommissionDao
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BalanceEntity
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.CommissionEntity

@Database(version = 1, entities = [BalanceEntity::class, CommissionEntity::class])
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
    abstract fun commissionDao(): CommissionDao
}