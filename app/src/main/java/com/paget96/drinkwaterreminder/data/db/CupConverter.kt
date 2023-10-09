package com.paget96.drinkwaterreminder.data.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ProvidedTypeConverter
class CupConverter @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun stringToCup(string: String?): Cup? {
        if (string == null) return null
        return gson.fromJson(string, Cup::class.java)
    }

    @TypeConverter
    fun cupToString(cup: Cup?): String? {
        if (cup == null) return null
        return gson.toJson(cup)
    }
}