package com.paget96.drinkwaterreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WateringRecord::class], version = 1)
abstract class DrinkWaterReminderDatabase : RoomDatabase() {

    abstract fun wateringRecordDao(): WateringRecordDao
}