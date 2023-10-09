package com.paget96.drinkwaterreminder.features.records.cup

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.SelectedCup
import com.paget96.drinkwaterreminder.databinding.DialogSwitchCupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CupDialogFragment : DialogFragment(), CupAdapter.OnItemClickListener {

    private var _binding: DialogSwitchCupBinding? = null
    private val binding: DialogSwitchCupBinding
        get() = requireNotNull(_binding)

    private val sharedViewModel: CupViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSwitchCupBinding.inflate(layoutInflater)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.switch_cup))
            .setCancelable(true)
            .setView(binding.root)
            .setNegativeButton(resources.getString(R.string.cancel), null)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cupAdapter = CupAdapter(this)

        val gridLayoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.VERTICAL,
            false
        ).apply {
            isSmoothScrollbarEnabled = true
        }

        with(binding.recycler) {
            adapter = cupAdapter
            setHasFixedSize(true)
            isNestedScrollingEnabled = true
            clipToPadding = false
            clipChildren = false
            layoutManager = gridLayoutManager
        }

        sharedViewModel.cups.observe(viewLifecycleOwner) { cups ->
            cupAdapter.submitList(cups)
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            sharedViewModel.cupEvent.collect { event ->
                when (event) {
                    CupViewModel.CupEvent.CupSelected -> dismiss()
                }
            }
        }
    }

    override fun onItemSelectedClick(cup: SelectedCup) {
        sharedViewModel.onSelectedCup(cup)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}