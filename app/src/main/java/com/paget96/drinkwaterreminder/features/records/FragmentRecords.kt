package com.paget96.drinkwaterreminder.features.records

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.CupType
import com.paget96.drinkwaterreminder.data.db.WateringRecord
import com.paget96.drinkwaterreminder.databinding.FragmentRecordsBinding
import com.paget96.drinkwaterreminder.features.records.cup.CupViewModel
import com.paget96.drinkwaterreminder.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentRecords : Fragment(R.layout.fragment_records), RecordsAdapter.OnItemClickListener {

    private val viewModel: RecordsViewModel by viewModels()
    private val sharedViewModel: CupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRecordsBinding.bind(view)

        val recordsAdapter = RecordsAdapter(this)

        with(binding) {
            wateringRecordsRecycler.adapter = recordsAdapter

            addWater.setOnClickListener {
                val selectedCup = sharedViewModel.selectedCup.value ?: CupType.Cup100ML
                viewModel.insertRecord(
                    WateringRecord(
                        timeStamp = DateUtils.currentTimeUnix,
                        cupType = selectedCup,
                        amountOfWater = selectedCup.amountOfWater,
                        isUpcoming = false
                    )
                )
            }

            switchCup.setOnClickListener { view ->
                val action = FragmentRecordsDirections
                    .actionFragmentRecordsToSwitchCupDialogFragment()
                view.findNavController().navigate(action)
            }
        }

        viewModel.waterLimit.observe(viewLifecycleOwner) { waterLimit ->
            binding.progressBar.setSecondaryProgress(waterLimit)
        }

        viewModel.maxProgress.observe(viewLifecycleOwner) { maxProgress ->
            binding.progressBar.setMax(maxProgress)
        }

        viewModel.amountOfWater.observe(viewLifecycleOwner) { amountOfWater ->
            binding.progressBar.setProgress(amountOfWater)
        }

        viewModel.waterLimitAndAmount.observe(viewLifecycleOwner) { (limit, amount) ->
            binding.currentProgress.text = requireContext().getString(
                R.string.water_filed_ml,
                "$amount",
                "$limit"
            )
        }

        viewModel.wateringRecords.observe(viewLifecycleOwner) { wateringRecords ->
            recordsAdapter.submitList(wateringRecords)
        }

        sharedViewModel.selectedCup.observe(viewLifecycleOwner) { selectedCup ->
            binding.switchCup.icon = AppCompatResources.getDrawable(
                requireContext(),
                selectedCup.icon
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recordsEvent.collect { event ->
                when (event) {
                    is RecordsViewModel.RecordsEvent.ShowUndoDeleteRecordMessage -> {
                        Snackbar.make(requireView(), "Record deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClick(event.record)
                            }
                            .show()
                    }
                }
            }
        }
    }

    override fun onItemDeleteClick(record: WateringRecord) {
        viewModel.onItemDelete(record)
    }

    override fun onItemEditClick(id: Long) {
        viewModel.onItemEdit(id)
    }
}