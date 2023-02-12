package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view.BalanceView

class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun populate(data: BalanceData) {
        (itemView as? BalanceView)?.populate(data)
    }

}