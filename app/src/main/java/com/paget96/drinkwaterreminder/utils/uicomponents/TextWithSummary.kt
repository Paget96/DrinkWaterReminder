package com.paget96.drinkwaterreminder.utils.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.paget96.drinkwaterreminder.R

class TextWithSummary @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    // Variables
    var titleTextView: TextView? = null
    var summaryTextView: TextView? = null
    var drawableView: ImageView? = null
    var arrowView: ImageView? = null

    private fun initializeViews(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        // Load the styled attributes and set their properties
        val attributes =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TextWithSummary,
                defStyleAttr,
                0
            )

        val rootView: View? =
            LayoutInflater.from(context).inflate(R.layout.text_with_summary, this)

        titleTextView = rootView?.findViewById(R.id.title)
        summaryTextView = rootView?.findViewById(R.id.summary)
        drawableView = rootView?.findViewById(R.id.drawable)
        arrowView = rootView?.findViewById(R.id.arrow)

        // Set title
        setTitle(attributes.getString(R.styleable.TextWithSummary_title_text))

        // Set summary
        setSummary(attributes.getString(R.styleable.TextWithSummary_summary_text))

        // Set drawable
        setDrawable(
            attributes.getResourceId(
                R.styleable.TextWithSummary_drawable, 0
            )
        )

        showArrow(
            attributes.getBoolean(
                R.styleable.TextWithSummary_show_arrow, false
            )
        )

        setDrawablePadding(
            attributes.getDimension(
                R.styleable.TextWithSummary_drawable_padding_top, 0f
            ).toInt(),
            attributes.getDimension(
                R.styleable.TextWithSummary_drawable_padding_bottom, 0f
            ).toInt(),
            attributes.getDimension(
                R.styleable.TextWithSummary_drawable_padding_start, 0f
            ).toInt(),
            attributes.getDimension(
                R.styleable.TextWithSummary_drawable_padding_end, 0f
            ).toInt()
        )
        attributes.recycle()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        titleTextView?.isEnabled = enabled
        summaryTextView?.isEnabled = enabled
        drawableView?.isEnabled = enabled
        arrowView?.isEnabled = enabled
    }

    fun showArrow(show: Boolean) {
        arrowView?.visibility = if (show) VISIBLE else GONE
    }

    fun setTitle(title: String?) {
        if (title.isNullOrBlank())
            titleTextView?.visibility = GONE
        else
            titleTextView?.text = title
    }

    fun setSummary(summaryText: String?) {
        if (summaryText.isNullOrBlank())
            summaryTextView?.visibility = GONE
        else
            summaryTextView!!.text = summaryText
    }

    fun setDrawable(drawable: Int?) {
        if (drawable == null || drawable == 0)
            drawableView?.visibility = GONE
        else {
            drawableView?.visibility = VISIBLE
            drawableView!!.setImageResource(drawable)
        }
    }

    fun setDrawablePadding(
        paddingTop: Int,
        paddingBottom: Int,
        paddingStart: Int,
        paddingEnd: Int
    ) {
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(paddingStart, paddingTop, paddingEnd, paddingBottom)
        drawableView?.layoutParams = params

        //drawableView?.setPadding(valuePaddingStart.toInt(), valuePaddingTop.toInt(), valuePaddingEnd.toInt(), valuePaddingBottom.toInt())
    }

    init {
        initializeViews(context, attrs, defStyleAttr)
    }
}