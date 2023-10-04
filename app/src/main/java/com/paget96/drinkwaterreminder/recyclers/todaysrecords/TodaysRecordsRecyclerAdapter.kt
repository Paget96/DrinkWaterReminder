package com.paget96.drinkwaterreminder.recyclers.todaysrecords

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.database.stats.StatsDatabase
import com.paget96.drinkwaterreminder.database.stats.TodaysWateringRecordsEntity
import com.paget96.drinkwaterreminder.utils.DateUtils


class TodaysRecordsRecyclerAdapter(
    private val context: Context,
    private val list: MutableList<TodaysRecordsData>,
    private val statsDatabase: StatsDatabase,
    private val getTodaysRecords: () -> Unit
) :
    RecyclerView.Adapter<TodaysRecordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodaysRecordsViewHolder {
        //Inflate the layout, initialize the View Holder
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_watering_record, parent, false)
        return TodaysRecordsViewHolder(v)
    }

    override fun onBindViewHolder(holder: TodaysRecordsViewHolder, position: Int) {
        when {
            list[position].wateringType == -1 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_cup_customize
                    )
                )
            }

            list[position].wateringType == 0 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_small_cup
                    )
                )

            }

            list[position].wateringType == 1 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_mug
                    )
                )

            }

            list[position].wateringType == 2 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_glass
                    )
                )

            }

            list[position].wateringType == 3 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_glass_big
                    )
                )

            }

            list[position].wateringType == 4 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_can
                    )
                )

            }

            list[position].wateringType == 5 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_bottle
                    )
                )

            }

            list[position].wateringType == 6 -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_bike_bottle
                    )
                )
            }

            list[position].wateringType > list.size -> {
                holder.wateringType.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_water_cup_customize
                    )
                )
            }
        }

        holder.time.text = DateUtils.convertMsToDate(list[position].time, false, false, false)

        holder.isUpcoming.visibility = if (list[position].isUpcoming) View.VISIBLE else View.GONE
        holder.more.visibility = if (list[position].isUpcoming) View.INVISIBLE else View.VISIBLE

        holder.amountOfWater.text =
            context.getString(R.string.ml, list[position].amountOfWater.toString())

        holder.more.setOnClickListener {
            val popup = PopupMenu(context, holder.more)
            popup.menuInflater.inflate(R.menu.popup_menu_todays_watering_plan, popup.menu)

            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    if (item.itemId == R.id.action_delete) {
                        remove(list[holder.adapterPosition])
                        getTodaysRecords()
                    }

                    return true
                }
            })

            popup.show()
        }

        TooltipCompat.setTooltipText(holder.more, context.getString(R.string.more))

        //animate(holder);
    }

    override fun getItemCount(): Int {
        //returns the number of elements the RecyclerView will display
        return list.size
    }

    // Insert a new item to the RecyclerView on a predefined position
    fun insert(position: Int, TodaysRecordsData: TodaysRecordsData) {
        list.add(position, TodaysRecordsData)
        notifyItemInserted(position)
    }

    // Remove a RecyclerView item containing a specified AppUsageData object
    fun remove(TodaysRecordsData: TodaysRecordsData) {
        val position = list.indexOf(TodaysRecordsData)

        statsDatabase.todaysWateringRecordsDao.delete(
            TodaysWateringRecordsEntity(
                TodaysRecordsData.time,
                TodaysRecordsData.wateringType,
                TodaysRecordsData.amountOfWater,
                TodaysRecordsData.isUpcoming
            )
        )

        list.removeAt(position)
        notifyItemRemoved(position)
    }
}