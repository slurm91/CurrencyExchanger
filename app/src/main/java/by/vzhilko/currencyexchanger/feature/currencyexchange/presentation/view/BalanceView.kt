package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.util.TwoSignsDecimalFormatter
import com.google.android.material.textview.MaterialTextView

class BalanceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val decimalFormatter: TwoSignsDecimalFormatter = TwoSignsDecimalFormatter()
    private lateinit var balanceTextView: TextView

    init {
        initView()
    }

    private fun initView() {
        balanceTextView = MaterialTextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setTextAppearance(R.style.TextAppearance_Balance_Large)
        }
        addView(balanceTextView)
    }

    @SuppressLint("SetTextI18n")
    fun populate(data: BalanceData) {
        balanceTextView.text = "${decimalFormatter.format(data.balance)} ${data.currency.name}"
    }

}