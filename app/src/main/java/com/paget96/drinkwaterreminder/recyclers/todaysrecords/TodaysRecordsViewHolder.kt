package com.paget96.drinkwaterreminder.recyclers.todaysrecords

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paget96.drinkwaterreminder.R

class TodaysRecordsViewHolder internal constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    @JvmField
    val wateringType: ImageView = itemView.findViewById(R.id.watering_type)

    @JvmField
    val time: TextView = itemView.findViewById(R.id.time)

    @JvmField
    val isUpcoming: TextView = itemView.findViewById(R.id.is_upcoming)

    @JvmField
    val amountOfWater: TextView = itemView.findViewById(R.id.amount_of_water)

    @JvmField
    val more: ImageButton = itemView.findViewById(R.id.more)
}