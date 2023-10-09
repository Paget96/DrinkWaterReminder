package com.paget96.drinkwaterreminder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WateringRecord::class, SelectedCup::class], version = 1)
@TypeConverters(CupConverter::class)
abstract class DrinkWaterReminderDatabase : RoomDatabase() {

    abstract fun wateringRecordDao(): WateringRecordDao

    abstract fun selectedCupDao(): SelectedCupDao
}