package by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto

import java.math.BigDecimal

data class BalanceData(val balance: BigDecimal, val currency: CurrencyData)