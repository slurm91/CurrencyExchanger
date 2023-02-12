package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.ListAdapter
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder.BalanceViewHolder
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.diffutil.BalanceDiffUtil
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view.BalanceView

class BalancesListAdapter : ListAdapter<BalanceData, BalanceViewHolder>(BalanceDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = BalanceView(parent.context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }
        return BalanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

}