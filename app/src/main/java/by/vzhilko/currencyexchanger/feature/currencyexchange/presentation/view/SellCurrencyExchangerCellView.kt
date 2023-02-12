package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.core.widget.doAfterTextChanged
import by.vzhilko.currencyexchanger.R
import com.google.android.material.textfield.TextInputEditText

class SellCurrencyExchangerCellView(
    context: Context,
    attrs: AttributeSet? = null
) : CurrencyExchangerCellView(context, attrs) {

    override val iconDrawable: Drawable? get() = ContextCompat.getDrawable(context, R.drawable.ic_sell_currency_arrow)
    override val title: String get() = resources.getString(R.string.currency_exchanger_cell_sell_title)

    private lateinit var balanceInputTextView: TextInputEditText
    var onTextChangedAction: ((balance: String?) -> Unit)? = null

    override fun buildContentView(container: ViewGroup): View {
        balanceInputTextView = TextInputEditText(container.context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            filters = arrayOf(BalanceInputFilter())
            setPadding(0)
            doAfterTextChanged { text ->
                onTextChangedAction?.invoke(text?.toString())
            }
        }

        return balanceInputTextView
    }

    fun populate(data: String) {
        if (balanceInputTextView.text?.toString() != data) {
            balanceInputTextView.setText(data)
        }
    }

    class BalanceInputFilter : InputFilter {

        private val balanceRegex: Regex = Regex("^0\$|^[1-9]\\d*|\\d*\\.\\d{0,2}\$")

        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence {
            return if (balanceRegex.matches("$dest$source")) source ?: "" else ""
        }

    }

}