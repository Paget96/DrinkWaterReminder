package com.paget96.drinkwaterreminder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paget96.drinkwaterreminder.activities.MainActivity
import com.paget96.drinkwaterreminder.databinding.FragmentHistoryBinding
import com.paget96.drinkwaterreminder.utils.SafeAttachFragment

class FragmentHistory: SafeAttachFragment() {

    // Variables
    private var binding: FragmentHistoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (attached as MainActivity).collapsingToolbarLayout?.title = "History"

        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}