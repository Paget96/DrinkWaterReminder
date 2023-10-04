package com.paget96.drinkwaterreminder.di

import android.app.Application
import androidx.room.Room
import com.paget96.drinkwaterreminder.data.db.DrinkWaterReminderDatabase
import com.paget96.drinkwaterreminder.data.db.WateringRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWateringRecordDao(
        database: DrinkWaterReminderDatabase
    ): WateringRecordDao = database.wateringRecordDao()

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ): DrinkWaterReminderDatabase = Room.databaseBuilder(
        app,
        DrinkWaterReminderDatabase::class.java,
        "drink_water_reminder.db"
    ).fallbackToDestructiveMigration().build()
}