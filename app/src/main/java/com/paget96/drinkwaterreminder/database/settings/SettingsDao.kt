package com.paget96.drinkwaterreminder.database.settings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingsDao {

    @get:Query("SELECT * FROM settingsentity")
    val all: List<SettingsEntity?>?

    @Query("SELECT * FROM settingsentity WHERE settingName IN (:settingName)")
    fun loadAllByName(settingName: Array<String?>?): List<SettingsEntity?>?

    @Query("SELECT * FROM settingsentity WHERE settingName IN (:settingName)")
    fun findByName(settingName: String?): SettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg settings: SettingsEntity?)

    @Delete
    fun delete(setting: SettingsEntity?)
}