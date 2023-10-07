package com.paget96.drinkwaterreminder.data.db

import androidx.annotation.DrawableRes
import com.paget96.drinkwaterreminder.R

data class Cup(
    val id: Long,
    val cupType: CupType
)

enum class CupType(
    @DrawableRes val icon: Int,
    val amountOfWater: Float
) {
    Cup100ML(R.drawable.ic_water_small_cup, 100.0F),
    Cup125ML(R.drawable.ic_water_mug, 125.0F),
    Cup150L(R.drawable.ic_water_glass, 150.0F),
    Cup175ML(R.drawable.ic_water_glass_big, 175.0F),
    Cup200ML(R.drawable.ic_water_can, 200.0F),
    Cup300ML(R.drawable.ic_water_bottle, 300.0F),
    Cup400ML(R.drawable.ic_water_bike_bottle, 400.0F),
    CupCustom(R.drawable.ic_water_cup_customize, 0.0F)
}