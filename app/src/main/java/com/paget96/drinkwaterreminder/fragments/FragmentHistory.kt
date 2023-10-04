package com.paget96.drinkwaterreminder.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.activities.MainActivity

class FragmentHistory : Fragment(R.layout.fragment_history) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).collapsingToolbarLayout?.title = "History"
    }
}