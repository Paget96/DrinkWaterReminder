package com.paget96.drinkwaterreminder.database.settings

import androidx.room.*

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