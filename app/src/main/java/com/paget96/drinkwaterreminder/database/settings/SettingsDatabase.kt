package com.paget96.drinkwaterreminder.database.settings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SettingsEntity::class], version = 1, exportSchema = true)
abstract class SettingsDatabase : RoomDatabase() {

    abstract val settingsDao: SettingsDao
    fun getSettingsState(setting: String?, defaultState: String?): String {
        val entity = instance!!.settingsDao.findByName(setting)
        return if (entity != null) instance!!.settingsDao.findByName(setting)!!.settingState else defaultState.toString()
    }

    fun setSettingsState(setting: String?, state: String?) {
        instance!!.settingsDao.insertAll(SettingsEntity(setting!!, state!!))
    }

    fun destroyInstance() {
        instance = null
    }

    companion object {

        private const val DATABASE = "SettingsDatabase"
        private var instance: SettingsDatabase? = null
        fun getDatabase(context: Context?): SettingsDatabase? {
            if (instance == null) {
                synchronized(SettingsDatabase::class.java) {
                    if (instance == null) {
                        instance =
                            Room.databaseBuilder(context!!, SettingsDatabase::class.java, DATABASE)
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