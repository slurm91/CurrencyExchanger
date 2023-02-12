package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.BalanceData
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.BalancesListAdapter
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.decoration.BalanceViewItemDecoration
import com.google.android.material.textview.MaterialTextView

class BalancesListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var defaultContentPadding: Int = 0
    private lateinit var balancesListAdapter: BalancesListAdapter

    init {
        initView()
    }

    private fun initView() {
        orientation = VERTICAL
        defaultContentPadding = resources.getDimensionPixelSize(R.dimen.default_content_padding)
        initTextView()
        initRecyclerView()
    }

    private fun initTextView() {
        val textView = MaterialTextView(context).apply {
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(defaultContentPadding)
            }
            setTextAppearance(R.style.TextAppearance_Title_Capitalized_Gray)
            text = resources.getString(R.string.balances_list_title)
        }
        addView(textView)
    }

    private fun initRecyclerView() {
        balancesListAdapter = BalancesListAdapter()
        val recyclerView = RecyclerView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                topMargin = defaultContentPadding
                bottomMargin = defaultContentPadding
                adapter = balancesListAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                addItemDecoration(BalanceViewItemDecoration(defaultContentPadding))
            }
        }
        addView(recyclerView)
    }

    fun populate(list: List<BalanceData>) {
        balancesListAdapter.submitList(list)
    }

}