package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData

class BalanceComparator(private val baseCurrency: CurrencyData) : Comparator<BalanceData> {

    override fun compare(o1: BalanceData?, o2: BalanceData?): Int {
        return when {
            o1?.currency == baseCurrency -> -1
            o2?.currency == baseCurrency -> 1
            else -> {
                if (o1 != null && o2 != null) {
                    o1.currency.name.compareTo(o2.currency.name)
                } else {
                    0
                }
            }
        }
    }

}