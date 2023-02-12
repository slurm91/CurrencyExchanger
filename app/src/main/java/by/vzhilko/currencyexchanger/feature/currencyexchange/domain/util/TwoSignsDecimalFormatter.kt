package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util

import java.math.BigDecimal
import java.text.DecimalFormat

class TwoSignsDecimalFormatter {

    private val pattern: String = "##0.00"
    private val decimalFormat: DecimalFormat = DecimalFormat(pattern)

    fun format(value: BigDecimal): String {
        return decimalFormat.format(value)
    }

}