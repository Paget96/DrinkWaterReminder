package com.paget96.drinkwaterreminder.features.cup

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.CupType
import com.paget96.drinkwaterreminder.databinding.DialogSwitchCupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CupDialogFragment : DialogFragment(), CupAdapter.OnItemClickListener {

    private var _binding: DialogSwitchCupBinding? = null
    private val binding: DialogSwitchCupBinding
        get() = requireNotNull(_binding)

    private val sharedViewModel: CupViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSwitchCupBinding.inflate(layoutInflater)

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

        cupAdapter.submitList(sharedViewModel.cups)

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.switch_cup))
            .setCancelable(true)
            .setView(binding.root)
            .setPositiveButton(resources.getString(R.string.ok), null)
            .setNegativeButton(resources.getString(R.string.cancel), null)
            .create()
    }


    override fun onItemSelectedClick(cupType: CupType) {
        sharedViewModel.onSelectedCup(cupType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}