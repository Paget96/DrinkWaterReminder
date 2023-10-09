package com.paget96.drinkwaterreminder.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.paget96.drinkwaterreminder.data.db.CupConverter
import com.paget96.drinkwaterreminder.data.db.DrinkWaterReminderDatabase
import com.paget96.drinkwaterreminder.data.db.SelectedCupDao
import com.paget96.drinkwaterreminder.data.db.WateringRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideWateringRecordDao(
        database: DrinkWaterReminderDatabase
    ): WateringRecordDao = database.wateringRecordDao()

    @Provides
    fun provideSelectedCupDao(
        database: DrinkWaterReminderDatabase
    ): SelectedCupDao = database.selectedCupDao()

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application,
        cupConverter: CupConverter
    ): DrinkWaterReminderDatabase = Room.databaseBuilder(
        app,
        DrinkWaterReminderDatabase::class.java,
        "drink_water_reminder.db"
    )
        .addTypeConverter(cupConverter)
        .fallbackToDestructiveMigration()
        .build()
}