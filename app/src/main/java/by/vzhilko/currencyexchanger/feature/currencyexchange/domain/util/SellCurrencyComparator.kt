package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util

import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData

class SellCurrencyComparator(private val baseCurrency: CurrencyData) : Comparator<CurrencyData> {

    override fun compare(o1: CurrencyData?, o2: CurrencyData?): Int {
        return when {
            o1 == baseCurrency -> -1
            o2 == baseCurrency -> 1
            else -> {
                if (o1 != null && o2 != null) {
                    o1.name.compareTo(o2.name)
                } else {
                    0
                }
            }
        }
    }

}