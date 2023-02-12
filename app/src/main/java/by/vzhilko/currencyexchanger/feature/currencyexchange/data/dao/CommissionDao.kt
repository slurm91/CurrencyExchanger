package by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao

import androidx.room.*
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.COMMISSION_TABLE_ATTEMPTS_COUNT_FIELD_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.COMMISSION_TABLE_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.COMMISSION_TABLE_TITLE_FIELD_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.CommissionEntity

@Dao
interface CommissionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommission(entity: CommissionEntity)

    @Query("SELECT * FROM $COMMISSION_TABLE_NAME WHERE $COMMISSION_TABLE_TITLE_FIELD_NAME = :title")
    fun getCommission(title: String): CommissionEntity

    @Query("UPDATE $COMMISSION_TABLE_NAME SET $COMMISSION_TABLE_ATTEMPTS_COUNT_FIELD_NAME = :attemptsCount WHERE $COMMISSION_TABLE_TITLE_FIELD_NAME = :title")
    fun updateCommission(attemptsCount: Int, title: String): Int

}