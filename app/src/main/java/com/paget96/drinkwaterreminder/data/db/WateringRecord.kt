package com.paget96.drinkwaterreminder.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watering_record")
data class WateringRecord(
    @ColumnInfo(name = "time_stamp") val timeStamp: Long,
    @ColumnInfo(name = "watering_type") val wateringType: Int,
    @ColumnInfo(name = "amount_of_water") val amountOfWater: Float,
    @ColumnInfo(name = "is_upcoming") val isUpcoming: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)