package com.paget96.drinkwaterreminder.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WateringRecordDao {

    @Query("SELECT * FROM records ORDER BY id DESC")
    fun wateringRecords(): Flow<List<WateringRecord>>

    @Query("SELECT SUM(amount_of_water) FROM records")
    fun amountOfWater(): Flow<Float>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wateringRecord: WateringRecord)

    @Query("DELETE FROM records WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM records")
    suspend fun deleteAll()
}