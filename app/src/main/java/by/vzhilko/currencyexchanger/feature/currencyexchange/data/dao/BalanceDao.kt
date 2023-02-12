package by.vzhilko.currencyexchanger.feature.currencyexchange.data.dao

import androidx.room.*
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BALANCES_TABLE_BALANCE_FIELD_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BALANCES_TABLE_CURRENCY_FIELD_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BALANCES_TABLE_NAME
import by.vzhilko.currencyexchanger.feature.currencyexchange.data.entity.BalanceEntity

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalances(balancesList: List<BalanceEntity>)

    @Transaction
    fun insertAndGetBalances(balancesList: List<BalanceEntity>): List<BalanceEntity> {
        insertBalances(balancesList)
        return getBalances()
    }

    @Query("UPDATE $BALANCES_TABLE_NAME SET $BALANCES_TABLE_BALANCE_FIELD_NAME = :balance WHERE $BALANCES_TABLE_CURRENCY_FIELD_NAME = :currency")
    fun updateBalance(balance: Double, currency: String)

    @Transaction
    fun updateSellAndReceiveBalances(
        sellBalance: Double,
        sellCurrency: String,
        receiveBalance: Double,
        receiveCurrency: String
    ) {
        updateBalance(sellBalance, sellCurrency)
        updateBalance(receiveBalance, receiveCurrency)
    }

    @Query("SELECT * FROM $BALANCES_TABLE_NAME")
    fun getBalances(): List<BalanceEntity>

    @Query("SELECT COUNT(*) FROM $BALANCES_TABLE_NAME")
    fun getBalancesCount(): Long

}