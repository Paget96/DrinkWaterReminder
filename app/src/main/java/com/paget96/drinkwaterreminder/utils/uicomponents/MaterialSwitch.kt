package com.paget96.drinkwaterreminder.utils.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.paget96.drinkwaterreminder.R

class MaterialSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    // Variables
    var clickListener: OnClickListener? = null
    var titleTextView: TextView? = null
    var summaryTextView: TextView? = null
    var toggleMaterialSwitch: SwitchMaterial? = null


    private fun initializeViews(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {

        // Load the styled attributes and set their properties
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.MaterialSwitch, defStyleAttr, 0)

        val rootView: View? =
            LayoutInflater.from(context).inflate(R.layout.material_switch_with_summary, this)

        titleTextView = rootView?.findViewById(R.id.title)
        summaryTextView = rootView?.findViewById(R.id.summary)
        toggleMaterialSwitch = rootView?.findViewById(R.id.toggle)

        // Set title
        setTitle(attributes.getString(R.styleable.MaterialSwitch_switch_title))

        // Set summary
        setSummary(attributes.getString(R.styleable.MaterialSwitch_switch_summary))

        attributes.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    override fun onClick(p0: View?) {
        toggleMaterialSwitch?.performClick()

        // This should be below all the code
        clickListener?.onClick(p0)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        toggleMaterialSwitch?.isEnabled = enabled
        titleTextView?.isEnabled = enabled
        summaryTextView?.isEnabled = enabled
    }

    var isChecked: Boolean = false
        get() {
            return toggleMaterialSwitch?.isChecked == true
        }
        set(value) {
            field = value

            toggleMaterialSwitch?.isChecked = value
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

    init {
        initializeViews(context, attrs, defStyleAttr)
        super.setOnClickListener(this)
    }
}