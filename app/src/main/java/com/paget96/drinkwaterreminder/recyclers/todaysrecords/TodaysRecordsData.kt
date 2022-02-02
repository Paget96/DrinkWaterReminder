package com.paget96.drinkwaterreminder.recyclers.todaysrecords

import android.graphics.drawable.Drawable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

class TodaysRecordsData(
    val wateringType: Int,
    val time: Long,
    val amountOfWater: Float,
    val isUpcoming: Boolean
)