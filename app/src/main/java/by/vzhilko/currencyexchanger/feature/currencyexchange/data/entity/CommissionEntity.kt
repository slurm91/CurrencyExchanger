package by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val COMMISSION_TABLE_NAME: String = "commission_table"
const val COMMISSION_TABLE_ID_FIELD_NAME: String = "id"
const val COMMISSION_TABLE_TITLE_FIELD_NAME: String = "title"
const val COMMISSION_TABLE_ATTEMPTS_COUNT_FIELD_NAME: String = "attempts_count"
const val COMMISSION_TABLE_MAX_ATTEMPTS_COUNT_FIELD_NAME: String = "max_attempts_count"

@Entity(tableName = COMMISSION_TABLE_NAME)
data class CommissionEntity(
    @ColumnInfo(name = COMMISSION_TABLE_ID_FIELD_NAME)
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = COMMISSION_TABLE_TITLE_FIELD_NAME) val title: String,
    @ColumnInfo(name = COMMISSION_TABLE_ATTEMPTS_COUNT_FIELD_NAME) val attemptsCount: Int,
    @ColumnInfo(name = COMMISSION_TABLE_MAX_ATTEMPTS_COUNT_FIELD_NAME) val maxAttemptsCount: Int,
)