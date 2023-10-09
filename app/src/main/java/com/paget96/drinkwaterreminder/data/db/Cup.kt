package com.paget96.drinkwaterreminder.data.db

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paget96.drinkwaterreminder.R

@Entity(tableName = "cups")
data class Cup(
    @ColumnInfo(name = "cup_type") val cupType: CupType,
    @ColumnInfo(name = "amount_of_water") val amountOfWater: Float,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
) {
    companion object {
        val default = Cup(CupType.Cup100ML, 100.0F, 1)
    }
}

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

