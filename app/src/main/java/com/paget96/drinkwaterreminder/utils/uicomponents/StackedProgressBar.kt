package com.paget96.drinkwaterreminder.utils.uicomponents

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar

class StackedProgressBar : ProgressBar {

    // Variables
    var primaryProgress = 0
    var maxValue = 0

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @Synchronized
    override fun setMax(max: Int) {
        maxValue = max
        super.setMax(max)
    }

    @Synchronized
    override fun setProgress(progress: Int) {
        var getProgress = progress
        if (getProgress > maxValue) {
            getProgress = maxValue
        }
        primaryProgress = getProgress
        super.setProgress(getProgress)
    }

    @Synchronized
    override fun setSecondaryProgress(secondaryProgress: Int) {
        var getSecondaryProgress = secondaryProgress
        if (primaryProgress + getSecondaryProgress > maxValue) {
            getSecondaryProgress = maxValue - primaryProgress
        }
        super.setSecondaryProgress(primaryProgress + getSecondaryProgress)
    }

    private fun init() {
        val paint = Paint()
        paint.color = Color.BLACK
        primaryProgress = 0
        maxValue = 100
    }
}