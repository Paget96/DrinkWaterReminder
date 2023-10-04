package com.paget96.drinkwaterreminder.features.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paget96.drinkwaterreminder.databinding.FragmentHomeBinding
import com.paget96.drinkwaterreminder.utils.NumberFormatter
import com.paget96.drinkwaterreminder.utils.SafeAttachFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : SafeAttachFragment() {

    // Variables
    private var binding: FragmentHomeBinding? = null
    private val numberFormatter = NumberFormatter()
    private val waterLimitToday = 3050f
    private var amountOfWaterToday = 0f

    private fun viewState() {
        setSwitchCupButtonIcon(attached!!)
        getTodaysRecords()
    }

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

    private fun getTodaysRecords() {
//        lifecycleScope.launch(Dispatchers.Default) {
//            val data: MutableList<TodaysRecordsData> = ArrayList()
//
//            val todaysWateringRecordsDatabase =
//                (attached as MainActivity).statsDatabase!!.todaysWateringRecords
//
//            // Reset counter to 0
//            amountOfWaterToday = 0f
//
//            todaysWateringRecordsDatabase?.forEach {
//                amountOfWaterToday += it!!.amountOfWater
//
//                data.add(
//                    TodaysRecordsData(
//                        it.wateringType,
//                        it.timeStamp,
//                        it.amountOfWater,
//                        it.isUpcoming
//                    )
//                )
//            }
//
//            withContext(Dispatchers.Main) {
//                if (data.size == 0) {
//                    binding?.apply {
//                        //progressLayout.visibility = View.VISIBLE
//                        wateringRecordsRecycler.visibility = View.GONE
//                        //loadingProgress.visibility = View.GONE
//                        //loadingProgressText.visibility = View.GONE
//                        //noLog.visibility = View.VISIBLE
//                    }
//
//                } else {
//                    binding?.apply {
//                        //binding!!.progressLayout.visibility = View.GONE
//                        wateringRecordsRecycler.visibility = View.VISIBLE
//                        val adapter = TodaysRecordsRecyclerAdapter(
//                            attached!!,
//                            data, (attached as MainActivity).statsDatabase!!
//                        ) { getTodaysRecords() }
//                        wateringRecordsRecycler.adapter = adapter
//                    }
//                }
//
//                binding?.apply {
//                    // Define max limit a bit over the daily limit
//                    progressBar.setMax(waterLimitToday + (waterLimitToday * 20f / 100f))
//                    progressBar.setSecondaryProgress(waterLimitToday)
//
//                    // Apply current progress
//                    progressBar.setProgress(amountOfWaterToday)
//                    currentProgress.text = attached!!.getString(
//                        R.string.water_filed_ml,
//                        progressBar.getProgress().toInt().toString(),
//                        waterLimitToday.toInt().toString()
//                    )
//
//                    wateringRecordsRecycler.setHasFixedSize(true)
//                    wateringRecordsRecycler.setItemViewCacheSize(20)
//                    wateringRecordsRecycler.isNestedScrollingEnabled = true
//                    val linearLayoutManager: LinearLayoutManager =
//                        object : LinearLayoutManager(attached, VERTICAL, true) {
//                            override fun smoothScrollToPosition(
//                                recyclerView: RecyclerView,
//                                state: RecyclerView.State,
//                                position: Int
//                            ) {
//                                val smoothScroller: LinearSmoothScroller =
//                                    object : LinearSmoothScroller(attached) {
//                                        val SPEED = 300f
//                                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
//                                            return SPEED / displayMetrics.densityDpi
//                                        }
//                                    }
//                                smoothScroller.targetPosition = position
//                                startSmoothScroll(smoothScroller)
//                            }
//                        }
//                    linearLayoutManager.reverseLayout = true
//                    linearLayoutManager.stackFromEnd = true
//                    linearLayoutManager.isSmoothScrollbarEnabled = true
//                    wateringRecordsRecycler.layoutManager = linearLayoutManager
//                }
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewState()
        viewFunction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}