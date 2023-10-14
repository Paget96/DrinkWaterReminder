package com.paget96.drinkwaterreminder.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "selected_cup")
data class SelectedCup(
    val cup: Cup,
    @PrimaryKey(autoGenerate = false) val id: Long = 0L
) : Parcelable {
    companion object {
        val default = SelectedCup(Cup.default)
    }
}