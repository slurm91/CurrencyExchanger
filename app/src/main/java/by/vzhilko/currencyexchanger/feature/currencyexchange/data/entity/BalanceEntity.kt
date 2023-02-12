package by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

const val BALANCES_TABLE_NAME: String = "balances_table"
const val BALANCES_TABLE_ID_FIELD_NAME: String = "id"
const val BALANCES_TABLE_CURRENCY_FIELD_NAME: String = "currency"
const val BALANCES_TABLE_BALANCE_FIELD_NAME: String = "balance"

@Entity(
    tableName = BALANCES_TABLE_NAME,
    indices = [Index(value = [BALANCES_TABLE_CURRENCY_FIELD_NAME], unique = true)]
)
data class BalanceEntity(
     @ColumnInfo(name = BALANCES_TABLE_ID_FIELD_NAME)
     @PrimaryKey(autoGenerate = true)
     val id: Long = 0L,
     @ColumnInfo(name = BALANCES_TABLE_CURRENCY_FIELD_NAME) val currency: String,
     @ColumnInfo(name = BALANCES_TABLE_BALANCE_FIELD_NAME) val balance: Double
)