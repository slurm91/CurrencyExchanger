package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.diffutil.CurrencyDiffUtil
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder.CurrencyViewHolder
import com.google.android.material.textview.MaterialTextView

class TextCurrenciesListAdapter : ListAdapter<CurrencyData, CurrencyViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = MaterialTextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setTextAppearance(R.style.TextAppearance_Balance_Medium)
        }

        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.populate(getItem(position))
    }

}