package com.paget96.drinkwaterreminder.database.stats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [StatsEntity::class, TodaysWateringRecordsEntity::class], version = 2,
    exportSchema = true
)

//@TypeConverters(Converters::class)
abstract class StatsDatabase : RoomDatabase() {

    abstract val statsDao: StatsDao
    abstract val todaysWateringRecordsDao: TodaysWateringRecordsDao
    /*abstract val appDischargingMahDao: AppDischargingMahDao
    abstract val chargingMaHistoryDao: ChargingMaHistoryDao
    abstract val dischargingMaHistoryDao: DischargingMaHistoryDao
    abstract val temperatureHistoryDao: TemperatureHistoryDao
    abstract val dischargingHistoryDao: DischargingHistoryDao
    abstract val chargingHistoryDao: ChargingHistoryDao*/

    /**
     * Get list of info for passed entry name
     * First parameter is entry name, second is default state in case that nothing is returned
     */
    fun getStatsState(statsEntry: String?, defaultState: String): String {
        val entity = instance!!.statsDao.findByName(statsEntry)
            ?: return defaultState
        return entity.statsEntryState
    }

    /**
     * Use this to add new info entry in database
     * First parameter is entry name, second is entry value
     */
    fun setStatsState(statsEntry: String?, statsEntryState: String?) {
        instance!!.statsDao.insertAll(
            StatsEntity(
                statsEntry!!,
                statsEntryState!!
            )
        )
    }

    /**
     * Get today's watering plan
     */
    val todaysWateringRecords: List<TodaysWateringRecordsEntity?>?
        get() = instance!!.todaysWateringRecordsDao.todaysWateringRecords

    /**
     * Use this to add new watering plan entries
     */
    fun setTodaysWateringRecords(
        timeStamp: Long,
        wateringType: Int,
        amountOfWater: Float,
        isUpcoming: Boolean
    ) {
        instance!!.todaysWateringRecordsDao.insertAll(
            TodaysWateringRecordsEntity(
                timeStamp,
                wateringType,
                amountOfWater,
                isUpcoming
            )
        )
    }

    /**
     * Destroy current instance of database
     */
    fun destroyInstance() {
        instance = null
    }

    companion object {
        private const val DATABASE = "StatsDatabase"
        private var instance: StatsDatabase? = null
        fun getDatabase(context: Context?): StatsDatabase? {
            if (instance == null) {
                synchronized(StatsDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context!!,
                            StatsDatabase::class.java, DATABASE
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            //.enableMultiInstanceInvalidation()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}