package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import by.vzhilko.currencyexchanger.R
import com.google.android.material.textview.MaterialTextView

class ReceiveCurrencyExchangerCellView(
    context: Context,
    attrs: AttributeSet? = null
) : CurrencyExchangerCellView(context, attrs) {

    override val iconDrawable: Drawable? get() = ContextCompat.getDrawable(context, R.drawable.ic_receive_currency_arrow)
    override val title: String get() = resources.getString(R.string.currency_exchanger_cell_receive_title)

    private lateinit var balanceTextView: TextView

    override fun buildContentView(container: ViewGroup): View {
        balanceTextView = MaterialTextView(container.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setTextAppearance(R.style.TextAppearance_Balance_Medium_Green)
            gravity = Gravity.END
        }

        return balanceTextView
    }

    fun populate(data: String) {
        balanceTextView.text = data
    }

}