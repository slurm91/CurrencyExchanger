package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populate(data: CurrencyData) {
        (itemView as TextView).text = data.name
    }

}