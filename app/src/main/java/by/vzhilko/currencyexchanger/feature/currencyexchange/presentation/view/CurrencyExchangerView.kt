package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.setMargins
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import com.google.android.material.textview.MaterialTextView

class CurrencyExchangerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var defaultContentPadding: Int = 0

    private lateinit var sellCurrencyExchangerCellView: SellCurrencyExchangerCellView
    private lateinit var receiveCurrencyExchangerCellView: ReceiveCurrencyExchangerCellView

    var onTextChangedAction: ((balance: String?) -> Unit)?
        get() = sellCurrencyExchangerCellView.onTextChangedAction
        set(value) {
            sellCurrencyExchangerCellView.onTextChangedAction = value
        }
    var onSellCurrencySelectedAction: ((currency: CurrencyData) -> Unit)?
        get() = sellCurrencyExchangerCellView.onCurrencySelectedAction
        set(value) {
            sellCurrencyExchangerCellView.onCurrencySelectedAction = value
        }
    var onReceiveCurrencySelectedAction: ((currency: CurrencyData) -> Unit)?
        get() = receiveCurrencyExchangerCellView.onCurrencySelectedAction
        set(value) {
            receiveCurrencyExchangerCellView.onCurrencySelectedAction = value
        }

    init {
        initView()
    }

    private fun initView() {
        orientation = VERTICAL
        defaultContentPadding = resources.getDimensionPixelSize(R.dimen.default_content_padding)
        initTextView()
        initSellCurrencyExchangerCellView()
        initReceiveCurrencyExchangerCellView()
    }

    private fun initTextView() {
        val textView = MaterialTextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(defaultContentPadding)
                }
            setTextAppearance(R.style.TextAppearance_Title_Capitalized_Gray)
            text = resources.getString(R.string.currency_exchanger_title)
        }
        addView(textView)
    }

    private fun initSellCurrencyExchangerCellView() {
        sellCurrencyExchangerCellView = SellCurrencyExchangerCellView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }
        addView(sellCurrencyExchangerCellView)
    }

    private fun initReceiveCurrencyExchangerCellView() {
        receiveCurrencyExchangerCellView = ReceiveCurrencyExchangerCellView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }
        addView(receiveCurrencyExchangerCellView)
    }

    fun populateSellCurrency(data: CurrencyData) {
        sellCurrencyExchangerCellView.setCurrency(data)
    }

    fun populateSellCurrenciesList(data: List<CurrencyData>) {
        sellCurrencyExchangerCellView.populate(data)
    }

    fun populateReceiveCurrenciesList(data: List<CurrencyData>) {
        receiveCurrencyExchangerCellView.populate(data)
    }

    fun populateReceiveCurrencyBalance(data: String) {
        receiveCurrencyExchangerCellView.populate(data)
    }

}