package com.paget96.drinkwaterreminder.database.settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SettingsEntity(
    @field:PrimaryKey var settingName: String,
    @field:ColumnInfo(name = "setting_state") var settingState: String
)