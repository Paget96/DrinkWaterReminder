package com.paget96.drinkwaterreminder.recyclers.switchcup

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paget96.drinkwaterreminder.R

class SwitchCupViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @JvmField
    val wateringType: ImageView = itemView.findViewById(R.id.watering_type)

    @JvmField
    val amountOfWater: TextView = itemView.findViewById(R.id.amount_of_water)

}