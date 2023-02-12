package by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import by.vzhilko.currencyexchanger.R
import by.vzhilko.currencyexchanger.feature.currencyexchange.domain.dto.CurrencyData
import by.vzhilko.currencyexchanger.feature.currencyexchange.presentation.adapter.holder.CurrenciesListAdapter
import com.google.android.material.textview.MaterialTextView

abstract class CurrencyExchangerCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var defaultContentPadding: Int = 0

    private lateinit var iconImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var iconTitleSpace: Space
    private lateinit var containerView: FrameLayout
    private lateinit var currencySpinner: Spinner
    private lateinit var currenciesListAdapter: ArrayAdapter<CurrencyData>
    private lateinit var dividerView: View

    var onCurrencySelectedAction: ((currency: CurrencyData) -> Unit)? = null

    abstract val iconDrawable: Drawable?
    abstract val title: String

    init {
        initView()
    }

    private fun initView() {
        defaultContentPadding = resources.getDimensionPixelSize(R.dimen.default_content_padding)
        initImageView()
        initTitleTextView()
        initIconTitleSpace()
        initContainerView()
        initCurrencySpinner()
        initDividerView()
        setupViews()
    }

    private fun initImageView() {
        iconImageView = AppCompatImageView(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                setMargins(defaultContentPadding)
            }
            iconDrawable?.let { setImageDrawable(it) }
        }
        addView(iconImageView)
    }

    private fun initTitleTextView() {
        titleTextView = MaterialTextView(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setTextAppearance(R.style.TextAppearance_Title_Bold)
            text = title
        }
        addView(titleTextView)
    }

    private fun initIconTitleSpace() {
        iconTitleSpace = Space(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(defaultContentPadding, LayoutParams.WRAP_CONTENT)
        }
        addView(iconTitleSpace)
    }

    private fun initContainerView() {
        containerView = FrameLayout(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
                leftMargin = defaultContentPadding
                rightMargin = defaultContentPadding
            }
            addView(buildContentView(this))
        }
        addView(containerView)
    }

    abstract fun buildContentView(container: ViewGroup): View

    private fun initCurrencySpinner() {
        currenciesListAdapter = CurrenciesListAdapter(context)
        currencySpinner = Spinner(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            adapter = currenciesListAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val currencyData: CurrencyData? = parent?.getItemAtPosition(position) as CurrencyData?
                    currencyData?.let { onCurrencySelectedAction?.invoke(it) }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
        addView(currencySpinner)
    }

    private fun initDividerView() {
        dividerView = View(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(
                0,
                resources.getDimensionPixelSize(R.dimen.currency_exchanger_cell_divider_view_height)
            )
            setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray))
        }
        addView(dividerView)
    }

    private fun setupViews() {
        ConstraintSet().apply {
            clone(this@CurrencyExchangerCellView)

            connect(iconImageView.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
            connect(iconImageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(iconImageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            connect(iconTitleSpace.id, ConstraintSet.LEFT, iconImageView.id, ConstraintSet.RIGHT)
            connect(iconTitleSpace.id, ConstraintSet.TOP, iconImageView.id, ConstraintSet.TOP)
            connect(iconTitleSpace.id, ConstraintSet.BOTTOM, iconImageView.id, ConstraintSet.BOTTOM)

            connect(titleTextView.id, ConstraintSet.LEFT, iconTitleSpace.id, ConstraintSet.RIGHT)
            connect(titleTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(titleTextView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            connect(containerView.id, ConstraintSet.LEFT, titleTextView.id, ConstraintSet.RIGHT)
            connect(containerView.id, ConstraintSet.RIGHT, currencySpinner.id, ConstraintSet.LEFT)
            connect(containerView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(containerView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            connect(currencySpinner.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
            connect(currencySpinner.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(currencySpinner.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

            connect(dividerView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(dividerView.id, ConstraintSet.LEFT, titleTextView.id, ConstraintSet.LEFT)
            connect(dividerView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)

            applyTo(this@CurrencyExchangerCellView)
        }
    }

    fun setCurrency(currency: CurrencyData) {
        //val position: Int = currenciesListAdapter.getPosition(currency)
    }

    fun populate(data: List<CurrencyData>) {
        currenciesListAdapter.apply {
            setNotifyOnChange(true)
            addAll(data)
        }
    }

}