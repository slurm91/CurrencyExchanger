package by.vzhilko.currencyexchanger.core.util

import java.math.BigDecimal

fun String?.toBigDecimal(): BigDecimal? {
    return if (this == null || this.isEmpty()) null else BigDecimal(this)
}