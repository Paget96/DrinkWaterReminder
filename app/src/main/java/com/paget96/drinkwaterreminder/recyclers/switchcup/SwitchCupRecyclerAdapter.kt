package com.paget96.drinkwaterreminder.recyclers.switchcup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paget96.drinkwaterreminder.R
import com.paget96.drinkwaterreminder.database.stats.StatsDatabase
import com.paget96.drinkwaterreminder.utils.DateUtils

class SwitchCupRecyclerAdapter(
    private val context: Context,
    private val list: MutableList<SwitchCupData>
) :
    RecyclerView.Adapter<SwitchCupViewHolder>() {

    // Variables
    private val statsDatabase: StatsDatabase? = StatsDatabase.getDatabase(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwitchCupViewHolder {
        //Inflate the layout, initialize the View Holder
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_switch_cup, parent, false)
        return SwitchCupViewHolder(v)
    }

    override fun onBindViewHolder(holder: SwitchCupViewHolder, position: Int) {
        if (list.size - 1 == position) {
            holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize))

            holder.amountOfWater.text = context.getString(R.string.custom)

            holder.itemView.setOnClickListener{
                Toast.makeText(context, "LAST", Toast.LENGTH_SHORT).show()
            }
        } else {
            holder.amountOfWater.text = context.getString(R.string.ml, list[position].amountOfWater.toString())

            when {
                list[position].wateringType == -1 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize))

                }
                list[position].wateringType == 0 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_small_cup))

                }
                list[position].wateringType == 1 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_mug))

                }
                list[position].wateringType == 2 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_glass))

                }
                list[position].wateringType == 3 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_glass_big))

                }
                list[position].wateringType == 4 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_can))

                }
                list[position].wateringType == 5 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_bottle))

                }
                list[position].wateringType == 6 -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_bike_bottle))

                }
                list[position].wateringType > list.size -> {
                    holder.wateringType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_water_cup_customize))
                }
            }

            holder.itemView.setOnClickListener{
                Toast.makeText(context, list[position].amountOfWater.toString(), Toast.LENGTH_SHORT).show()
                statsDatabase?.setStatsState("cup_size", list[position].amountOfWater.toString())
                statsDatabase?.setStatsState("watering_type", list[position].wateringType.toString())
            }
        }


        //animate(holder);
    }

    override fun getItemCount(): Int {
        //returns the number of elements the RecyclerView will display
        return list.size
    }

    // Insert a new item to the RecyclerView on a predefined position
    fun insert(position: Int, SwitchCupData: SwitchCupData) {
        list.add(position, SwitchCupData)
        notifyItemInserted(position)
    }

    // Remove a RecyclerView item containing a specified AppUsageData object
    fun remove(SwitchCupData: SwitchCupData) {
        val position = list.indexOf(SwitchCupData)
        list.removeAt(position)
        notifyItemRemoved(position)
    }

}