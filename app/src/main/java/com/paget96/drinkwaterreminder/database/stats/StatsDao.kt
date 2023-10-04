package com.paget96.drinkwaterreminder.database.stats

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatsDao {

    @get:Query("SELECT * FROM statsentity")
    val all: List<StatsEntity?>?

    //@Query("SELECT * FROM batteryinfoentity WHERE batteryInfoEntry IN (:batteryInfoEntry)")
    //List<BatteryInfoEntity> loadAllByName(String[] batteryInfoEntry);

    @Query("SELECT * FROM statsentity WHERE statsEntry IN (:statsEntry)")
    fun findByName(statsEntry: String?): StatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg statsEntity: StatsEntity?)

    @Delete
    fun delete(setting: StatsEntity?)
}