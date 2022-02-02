package com.paget96.drinkwaterreminder.database.stats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TodaysWateringRecordsEntity(
    @field:PrimaryKey var timeStamp: Long,
    @field:ColumnInfo(name = "watering_type") var wateringType: Int,
    @field:ColumnInfo(name = "amount_of_water") var amountOfWater: Float,
    @field:ColumnInfo(name = "is_upcoming") var isUpcoming: Boolean
    )