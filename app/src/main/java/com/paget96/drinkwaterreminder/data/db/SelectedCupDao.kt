package com.paget96.drinkwaterreminder.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SelectedCupDao {

    @Query("SELECT * FROM selected_cup LIMIT 1")
    fun cup(): Flow<SelectedCup?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(selectedCup: SelectedCup)

    @Delete
    suspend fun delete(selectedCup: SelectedCup)
}