package com.paget96.drinkwaterreminder.features.records

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.data.db.WateringRecord
import com.paget96.drinkwaterreminder.databinding.FragmentRecordsBinding
import com.paget96.drinkwaterreminder.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentRecords : Fragment(R.layout.fragment_records), RecordsAdapter.OnItemClickListener {

    private val viewModel: RecordsViewModel by viewModels()

    // Variables
//    private var binding: FragmentHomeBinding? = null
//    private val numberFormatter = NumberFormatter()
    // private val waterLimitToday = 3050f
    // private var amountOfWaterToday = 0f

//    private fun viewState() {
//        setSwitchCupButtonIcon(attached!!)
//        getTodaysRecords()
//    }

//    private fun resetTodaysData() {
//        amountOfWaterToday = 0f
//
//        (attached as MainActivity).statsDatabase!!.todaysWateringRecordsDao.deleteAll()
//        getTodaysRecords()
//    }

    private fun setSwitchCupButtonIcon(context: Context) {
//        val wateringType = numberFormatter.parseIntWithDefault(
//            (attached as MainActivity).statsDatabase!!.getStatsState(
//                "watering_type",
//                "2"
//            ), 2
//        )
//        binding?.apply {
//            when {
//                wateringType == -1 -> {
//                    switchCup.icon =
//                        ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize)
//                }
//
//                wateringType == 0 -> {
//                    switchCup.icon =
//                        ContextCompat.getDrawable(context, R.drawable.ic_water_small_cup)
//                }
//
//                wateringType == 1 -> {
//                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_mug)
//                }
//
//                wateringType == 2 -> {
//                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_glass)
//                }
//
//                wateringType == 3 -> {
//                    switchCup.icon =
//                        ContextCompat.getDrawable(context, R.drawable.ic_water_glass_big)
//                }
//
//                wateringType == 4 -> {
//                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_can)
//                }
//
//                wateringType == 5 -> {
//                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_bottle)
//                }
//
//                wateringType == 6 -> {
//                    switchCup.icon =
//                        ContextCompat.getDrawable(context, R.drawable.ic_water_bike_bottle)
//                }
//
//                wateringType > 6 -> {
//                    switchCup.icon =
//                        ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize)
//                }
//            }
//        }
    }

    private fun viewFunction() {
//        binding?.apply {
//            // Add more water to the daily goal
//            addWater.setOnClickListener {
//                val cupSize = numberFormatter.parseFloatWithDefault(
//                    (attached as MainActivity).statsDatabase!!.getStatsState(
//                        "cup_size",
//                        "150"
//                    ), 150f
//                )
//                val wateringType = numberFormatter.parseIntWithDefault(
//                    (attached as MainActivity).statsDatabase!!.getStatsState(
//                        "watering_type",
//                        "2"
//                    ), 2
//                )
//
//                val currentWaterLevel = progressBar.getProgress() + cupSize
//
//                if (currentWaterLevel >= progressBar.getMax()) {
//                    progressBar.setMax(progressBar.getProgress() + cupSize)
//                }
//
//                progressBar.setProgress(currentWaterLevel)
//
//                currentProgress.text = attached!!.getString(
//                    R.string.water_filed_ml,
//                    progressBar.getProgress().toInt().toString(),
//                    waterLimitToday.toInt().toString()
//                )
//
//                (attached as MainActivity).statsDatabase!!.setStatsState(
//                    "water_filed",
//                    progressBar.getProgress().toInt().toString()
//                )
//
//                (attached as MainActivity).statsDatabase!!.setTodaysWateringRecords(
//                    DateUtils.currentTimeUnix,
//                    wateringType,
//                    cupSize,
//                    false
//                )
//
//                getTodaysRecords()
//            }
//
//            switchCup.setOnClickListener {
//                val dialog = MaterialAlertDialogBuilder(attached!!)
//
//                val customAlertDialogView = LayoutInflater.from(attached!!)
//                    .inflate(R.layout.dialog_switch_cup, null, false)
//
//                dialog.apply {
//                    setTitle(attached!!.getString(R.string.switch_cup))
//                    setCancelable(true)
//                    setView(customAlertDialogView)
//
//                    val recycler = customAlertDialogView.findViewById<RecyclerView>(R.id.recycler)
//                    lifecycleScope.launch(Dispatchers.Default) {
//                        val data: MutableList<SwitchCupData> = ArrayList()
//
//                        data.add(SwitchCupData(0, 100))
//                        data.add(SwitchCupData(1, 125))
//                        data.add(SwitchCupData(2, 150))
//                        data.add(SwitchCupData(3, 175))
//                        data.add(SwitchCupData(4, 200))
//                        data.add(SwitchCupData(5, 300))
//                        data.add(SwitchCupData(6, 400))
//                        data.add(SwitchCupData(-1, 0))
//
//                        withContext(Dispatchers.Main) {
//                            if (data.size == 0) {
//                                recycler?.visibility = View.GONE
//
//                            } else {
//                                recycler?.visibility = View.VISIBLE
//                                val adapter = SwitchCupRecyclerAdapter(attached!!, data)
//                                recycler?.adapter = adapter
//                            }
//
//                            binding?.apply {
//                                recycler?.setHasFixedSize(true)
//                                recycler?.setItemViewCacheSize(10)
//                                recycler?.isNestedScrollingEnabled = true
//                                recycler?.clipToPadding = false
//                                recycler?.clipChildren = false
//
//                                val gridLayoutManager = GridLayoutManager(
//                                    attached!!,
//                                    3,
//                                    LinearLayoutManager.VERTICAL,
//                                    false
//                                )
//
//                                gridLayoutManager.isSmoothScrollbarEnabled = true
//                                recycler?.layoutManager = gridLayoutManager
//                            }
//                        }
//                    }
//
//                    setPositiveButton(attached!!.getString(R.string.ok)) { dialogInterface, i -> }
//                    setNegativeButton(attached!!.getString(R.string.cancel)) { dialogInterface, i -> }
//
//                    setOnDismissListener {
//                        setSwitchCupButtonIcon(attached!!)
//                    }
//
//                    show()
//                }
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRecordsBinding.bind(view)

        val recordsAdapter = RecordsAdapter(this)

        with(binding) {
            wateringRecordsRecycler.adapter = recordsAdapter

            addWater.setOnClickListener {
                viewModel.insertRecord(
                    WateringRecord(
                        timeStamp = DateUtils.currentTimeUnix,
                        wateringType = 2,
                        amountOfWater = 150.0F,
                        isUpcoming = false
                    )
                )
            }

            switchCup.setOnClickListener { view ->
                val action =
                    FragmentRecordsDirections.actionFragmentRecordsToSwitchCupDialogFragment()
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
    }

    override fun onItemDeleteClick(id: Long) {
        viewModel.onItemDelete(id)
    }

    override fun onItemEditClick(id: Long) {
        viewModel.onItemEdit(id)
    }
}