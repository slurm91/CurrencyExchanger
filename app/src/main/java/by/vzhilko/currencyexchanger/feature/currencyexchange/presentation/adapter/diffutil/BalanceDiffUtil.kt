package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData

class BalanceDiffUtil : DiffUtil.ItemCallback<BalanceData>() {

    override fun areItemsTheSame(oldItem: BalanceData, newItem: BalanceData): Boolean {
        return oldItem.balance == newItem.balance && oldItem.currency == newItem.currency
    }

    override fun areContentsTheSame(oldItem: BalanceData, newItem: BalanceData): Boolean {
        return oldItem.balance == newItem.balance && oldItem.currency == newItem.currency
    }

}