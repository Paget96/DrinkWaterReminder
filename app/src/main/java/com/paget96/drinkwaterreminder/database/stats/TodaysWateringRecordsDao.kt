package com.paget96.drinkwaterreminder.database.stats

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodaysWateringRecordsDao {

    /**
     * Get list of charged mAh history
     */
    @get:Query("SELECT * FROM todayswateringrecordsentity")
    val todaysWateringRecords: List<TodaysWateringRecordsEntity?>?

    //@Query("SELECT * FROM chargingmahistoryentity WHERE timeStamp IN (:timeStamp)")
    //ChargingMaHistoryEntity findByTimestamp(long timeStamp);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todaysWateringRecordsEntity: TodaysWateringRecordsEntity?)

    @Delete
    fun delete(todaysWateringRecordsEntity: TodaysWateringRecordsEntity?)

    @Query("DELETE FROM todayswateringrecordsentity WHERE timestamp = :timestamp")
    fun deleteByTimestamp(timestamp: Long)

    @Query("DELETE FROM todayswateringrecordsentity")
    fun deleteAll()
}