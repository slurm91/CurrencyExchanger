package by.vzhilko.currencyexchanger.core.ui.view.progress

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import by.vzhilko.currencyexchanger.R

class FullScreenProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IProgressView {

    private lateinit var progressBar: ProgressBar

    init {
        initView()
    }

    private fun initView() {
        setBackgroundColor(ContextCompat.getColor(context, R.color.black_80))
        progressBar = ProgressBar(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }
        addView(progressBar)
        visibility = View.GONE
    }

    override fun showProgress() {
        visibility = View.VISIBLE
    }

    override fun hideProgress() {
        visibility = View.GONE
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

}