package com.paget96.drinkwaterreminder.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.activities.MainActivity
import com.paget96.drinkwaterreminder.databinding.FragmentMainBinding
import com.paget96.drinkwaterreminder.recyclers.switchcup.SwitchCupData
import com.paget96.drinkwaterreminder.recyclers.switchcup.SwitchCupRecyclerAdapter
import com.paget96.drinkwaterreminder.recyclers.todaysrecords.TodaysRecordsData
import com.paget96.drinkwaterreminder.recyclers.todaysrecords.TodaysRecordsRecyclerAdapter
import com.paget96.drinkwaterreminder.utils.DateUtils
import com.paget96.drinkwaterreminder.utils.NumberFormatter
import com.paget96.drinkwaterreminder.utils.SafeAttachFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class FragmentMain : SafeAttachFragment() {

    // Variables
    private var binding: FragmentMainBinding? = null
    private val numberFormatter = NumberFormatter()

    private fun viewState() {
        setSwitchCupButtonIcon(attached!!)

        binding?.apply {
            val waterLimitToday = 3050f
            val waterCurrent = numberFormatter.parseFloatWithDefault(
                (attached as MainActivity).statsDatabase!!.getStatsState(
                    "water_filed",
                    "0"
                ), 0f
            )

            // Define max limit a bit over the daily limit
            progressBar.max = waterLimitToday + (waterLimitToday * 20f / 100f)
            progressBar.secondaryProgress = waterLimitToday

            // Apply current progress
            progressBar.progress = waterCurrent
            currentProgress.text = attached!!.getString(
                R.string.water_filed_ml,
                progressBar.progress.toInt().toString(),
                waterLimitToday.toInt().toString()
            )

            // Add more water to the daily goal
            addWater.setOnClickListener {
                val cupSize = numberFormatter.parseFloatWithDefault(
                    (attached as MainActivity).statsDatabase!!.getStatsState(
                        "cup_size",
                        "150"
                    ), 150f
                )
                val wateringType = numberFormatter.parseIntWithDefault(
                    (attached as MainActivity).statsDatabase!!.getStatsState(
                        "watering_type",
                        "2"
                    ), 2
                )

                val currentWaterLevel = progressBar.progress + cupSize

                if (currentWaterLevel >= progressBar.max) {
                    progressBar.max = progressBar.progress + cupSize
                }

                progressBar.progress = currentWaterLevel

                currentProgress.text = attached!!.getString(
                    R.string.water_filed_ml,
                    progressBar.progress.toInt().toString(),
                    waterLimitToday.toInt().toString()
                )

                (attached as MainActivity).statsDatabase!!.setStatsState(
                    "water_filed",
                    progressBar.progress.toInt().toString()
                )

                (attached as MainActivity).statsDatabase!!.setTodaysWateringRecords(
                    DateUtils.currentTimeUnix,
                    wateringType,
                    cupSize,
                    false
                )

                getTodaysRecords()
            }

            switchCup.setOnClickListener {
                val dialog = MaterialAlertDialogBuilder(attached!!)

                val customAlertDialogView = LayoutInflater.from(attached!!)
                    .inflate(R.layout.dialog_switch_cup, null, false)

                dialog.apply {
                    setTitle(attached!!.getString(R.string.switch_cup))
                    setCancelable(true)
                    setView(customAlertDialogView)

                    val recycler = customAlertDialogView.findViewById<RecyclerView>(R.id.recycler)
                    lifecycleScope.launch(Dispatchers.Default) {
                        val data: MutableList<SwitchCupData> = ArrayList()

                        data.add(SwitchCupData(0, 100))
                        data.add(SwitchCupData(1, 125))
                        data.add(SwitchCupData(2, 150))
                        data.add(SwitchCupData(3, 175))
                        data.add(SwitchCupData(4, 200))
                        data.add(SwitchCupData(5, 300))
                        data.add(SwitchCupData(6, 400))
                        data.add(SwitchCupData(-1, 0))

                        withContext(Dispatchers.Main) {
                            if (data.size == 0) {
                                recycler?.visibility = View.GONE

                            } else {
                                recycler?.visibility = View.VISIBLE
                                val adapter = SwitchCupRecyclerAdapter(attached!!, data)
                                recycler?.adapter = adapter
                            }

                            binding?.apply {
                                recycler?.setHasFixedSize(true)
                                recycler?.setItemViewCacheSize(10)
                                recycler?.isNestedScrollingEnabled = true
                                recycler?.clipToPadding = false
                                recycler?.clipChildren = false

                                val gridLayoutManager = GridLayoutManager(
                                    attached!!,
                                    3,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )

                                gridLayoutManager.isSmoothScrollbarEnabled = true
                                recycler?.layoutManager = gridLayoutManager
                            }
                        }
                    }

                    setPositiveButton(attached!!.getString(R.string.ok)) { dialogInterface, i ->  resetTodaysData()}
                    setNegativeButton(attached!!.getString(R.string.cancel)) { dialogInterface, i -> }

                    setOnDismissListener {
                        setSwitchCupButtonIcon(attached!!)
                    }

                    show()
                }
            }

        }
    }

    private fun resetTodaysData() {
        (attached as MainActivity).statsDatabase!!.setStatsState(
            "water_filed", "0"
        )
        (attached as MainActivity).statsDatabase!!.todaysWateringRecordsDao.deleteAll()
        getTodaysRecords()
    }

    private fun setSwitchCupButtonIcon(context: Context) {
        val wateringType = numberFormatter.parseIntWithDefault(
            (attached as MainActivity).statsDatabase!!.getStatsState(
                "watering_type",
                "2"
            ), 2
        )
        binding?.apply {
            when {
                wateringType == -1 -> {
                    switchCup.icon =
                        ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize)
                }
                wateringType == 0 -> {
                    switchCup.icon =
                        ContextCompat.getDrawable(context, R.drawable.ic_water_small_cup)
                }
                wateringType == 1 -> {
                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_mug)
                }
                wateringType == 2 -> {
                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_glass)
                }
                wateringType == 3 -> {
                    switchCup.icon =
                        ContextCompat.getDrawable(context, R.drawable.ic_water_glass_big)
                }
                wateringType == 4 -> {
                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_can)
                }
                wateringType == 5 -> {
                    switchCup.icon = ContextCompat.getDrawable(context, R.drawable.ic_water_bottle)
                }
                wateringType == 6 -> {
                    switchCup.icon =
                        ContextCompat.getDrawable(context, R.drawable.ic_water_bike_bottle)
                }
                wateringType > 6 -> {
                    switchCup.icon =
                        ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize)
                }
            }
        }
    }

    private fun viewFunction() {
        getTodaysRecords()
    }

    private fun getTodaysRecords() {
        lifecycleScope.launch(Dispatchers.Default) {
            val data: MutableList<TodaysRecordsData> = ArrayList()

            val todaysWateringRecordsDatabase =
                (attached as MainActivity).statsDatabase!!.todaysWateringRecords

            todaysWateringRecordsDatabase?.forEach {
                data.add(
                    TodaysRecordsData(
                        it!!.wateringType,
                        it.timeStamp,
                        it.amountOfWater,
                        it.isUpcoming
                    )
                )
            }

            withContext(Dispatchers.Main) {
                if (data.size == 0) {
                    binding?.apply {
                        //progressLayout.visibility = View.VISIBLE
                        wateringPlanRecycler.visibility = View.GONE
                        //loadingProgress.visibility = View.GONE
                        //loadingProgressText.visibility = View.GONE
                        //noLog.visibility = View.VISIBLE
                    }

                } else {
                    binding?.apply {
                        //binding!!.progressLayout.visibility = View.GONE
                        wateringPlanRecycler.visibility = View.VISIBLE
                        val adapter = TodaysRecordsRecyclerAdapter(attached!!, data, (attached as MainActivity).statsDatabase!!)
                        wateringPlanRecycler.adapter = adapter
                    }

                }

                binding?.apply {
                    wateringPlanRecycler.setHasFixedSize(true)
                    wateringPlanRecycler.setItemViewCacheSize(20)
                    wateringPlanRecycler.isNestedScrollingEnabled = true
                    val linearLayoutManager: LinearLayoutManager =
                        object : LinearLayoutManager(attached, VERTICAL, true) {
                            override fun smoothScrollToPosition(
                                recyclerView: RecyclerView,
                                state: RecyclerView.State,
                                position: Int
                            ) {
                                val smoothScroller: LinearSmoothScroller =
                                    object : LinearSmoothScroller(attached) {
                                        val SPEED = 300f
                                        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                                            return SPEED / displayMetrics.densityDpi
                                        }
                                    }
                                smoothScroller.targetPosition = position
                                startSmoothScroll(smoothScroller)
                            }
                        }
                    linearLayoutManager.isSmoothScrollbarEnabled = true
                    wateringPlanRecycler.layoutManager = linearLayoutManager
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (attached as MainActivity).collapsingToolbarLayout?.title = "Main"

        binding = FragmentMainBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
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