package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData

class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyData>() {

    override fun areItemsTheSame(oldItem: CurrencyData, newItem: CurrencyData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CurrencyData, newItem: CurrencyData): Boolean {
        return oldItem == newItem
    }

}