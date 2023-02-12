package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import com.google.android.material.textview.MaterialTextView

class CurrenciesListAdapter(context: Context) : ArrayAdapter<CurrencyData>(context, R.layout.view_currency_item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

    private fun getItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: MaterialTextView = inflater.inflate(
            R.layout.view_currency_item,
            parent,
            false
        ) as MaterialTextView

        getItem(position)?.let { view.text = it.name }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getItemView(position, convertView, parent)
    }

}