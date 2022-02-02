package com.paget96.drinkwaterreminder.utils

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

/*
 * A fragment class that can safely attach to activity without throwing NPE.
 * Extend from this, please.
 */
abstract class SafeAttachFragment : Fragment() {

    var attached: Activity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            attached = context
        }
    }
}