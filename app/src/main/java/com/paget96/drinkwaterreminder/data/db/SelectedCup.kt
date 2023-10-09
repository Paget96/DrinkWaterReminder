package com.paget96.drinkwaterreminder.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "selected_cup")
data class SelectedCup(
    val cup: Cup,
    @PrimaryKey(autoGenerate = false) val id: Long = 0L
) {
    companion object {
        val default = SelectedCup(Cup.default)
    }
}
