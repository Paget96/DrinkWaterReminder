package com.paget96.drinkwaterreminder.data.db

import androidx.annotation.DrawableRes
import com.paget96.drinkwaterreminder.R

data class Cup(
    val id: Long,
    val cupType: CupType,
    val amountOfWater: Float
)

enum class CupType(
    @DrawableRes val icon: Int
) {
    Cup100ML(R.drawable.ic_water_small_cup),
    Cup125ML(R.drawable.ic_water_mug),
    Cup150L(R.drawable.ic_water_glass),
    Cup175ML(R.drawable.ic_water_glass_big),
    Cup200ML(R.drawable.ic_water_can),
    Cup300ML(R.drawable.ic_water_bottle),
    Cup400ML(R.drawable.ic_water_bike_bottle),
    CupCustom(R.drawable.ic_water_cup_customize)
}