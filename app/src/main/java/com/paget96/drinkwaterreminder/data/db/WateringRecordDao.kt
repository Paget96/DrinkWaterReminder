package com.paget96.drinkwaterreminder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WateringRecordDao {

    @Query("SELECT * FROM watering_record ORDER BY id DESC")
    fun wateringRecords(): Flow<List<WateringRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wateringRecord: WateringRecord)

    @Query("DELETE FROM watering_record WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM watering_record")
    suspend fun deleteAll()
}