package com.paget96.batteryguru.utils.database.batteryinfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StatsEntity(
    @field:PrimaryKey var statsEntry: String,
    @field:ColumnInfo(name = "stats_entry_state") var statsEntryState: String
)