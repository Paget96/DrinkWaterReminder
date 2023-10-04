package com.paget96.drinkwaterreminder.features.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.databinding.FragmentOtherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentOther : Fragment(R.layout.fragment_other) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOtherBinding.bind(view)

        with(binding) {
            settings.setOnClickListener { view ->
                val action = FragmentOtherDirections.actionFragmentOtherToFragmentSettings()
                view.findNavController().navigate(action)
            }

            about.setOnClickListener { view ->
                val action = FragmentOtherDirections.actionFragmentOtherToFragmentAbout()
                view.findNavController().navigate(action)
            }
        }
    }
}