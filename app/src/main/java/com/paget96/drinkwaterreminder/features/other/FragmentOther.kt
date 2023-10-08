package com.paget96.drinkwaterreminder.features.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.databinding.FragmentOtherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentOther : Fragment(R.layout.fragment_other) {

    private val viewModel: OtherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOtherBinding.bind(view)

        with(binding) {
            settings.setOnClickListener { viewModel.navigateToSettingsScreen() }

            about.setOnClickListener { viewModel.navigateToAboutScreen() }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.otherEvent.collect { event ->
                when (event) {
                    OtherViewModel.OtherEvent.NavigateToAboutScreen -> {
                        val action = FragmentOtherDirections.actionFragmentOtherToFragmentAbout()
                        findNavController().navigate(action)
                    }

                    OtherViewModel.OtherEvent.NavigateToSettingsScreen -> {
                        val action = FragmentOtherDirections.actionFragmentOtherToFragmentSettings()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}