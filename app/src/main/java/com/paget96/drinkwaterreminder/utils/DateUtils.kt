package com.paget96.drinkwaterreminder.utils

import android.content.Context
import com.paget96.drinkwaterreminder.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    @JvmStatic
    val currentTimeUnix: Long
        get() = System.currentTimeMillis()

    val timeZoneOffset: Long
        get() {
            val timeZone = TimeZone.getDefault()
            val calendar = Calendar.getInstance()
            return timeZone.getOffset(calendar.timeInMillis).toLong()
        }

    val startTimeOfCurrentDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar.timeInMillis
        }

    @JvmStatic
    fun elapsedTimeTillMidnight(): Long {
        val current = Calendar.getInstance() //now
        val midnight = Calendar.getInstance() //midnight
        midnight[Calendar.HOUR_OF_DAY] = 0
        midnight[Calendar.MINUTE] = 0
        midnight[Calendar.SECOND] = 0
        midnight[Calendar.MILLISECOND] = 0
        return current.timeInMillis - midnight.timeInMillis
    }

    @JvmStatic
    fun convertMsToDate(timeInMs: Long, useSeconds: Boolean, useMilliseconds: Boolean, showDate: Boolean): String {
        val simpleDateFormat =
            SimpleDateFormat(
                "HH:mm" + (if (useSeconds) ":ss" else "") + (if (useMilliseconds) ":SSS" else "") + (if (showDate)" dd.MM.yyyy" else ""),
                Locale.getDefault()
            )
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.format(timeInMs)
    }

    fun midnightPassed(): Boolean {
        val calendar = Calendar.getInstance()
        val hours = calendar[Calendar.HOUR_OF_DAY]
        val minutes = calendar[Calendar.MINUTE]
        val seconds = calendar[Calendar.SECOND]

        // Day changed since last task
        return hours * 3600 + minutes * 60 + seconds < 1800
    }

    @JvmStatic
    fun convertMsToTime(
        timeMs: Long,
        showSeconds: Boolean,
        showUnit: Boolean,
        context: Context
    ): String {
        val time: String
        val hours = TimeUnit.MILLISECONDS.toHours(timeMs).toInt()
        val min = (TimeUnit.MILLISECONDS.toMinutes(timeMs) % TimeUnit.HOURS.toMinutes(1)).toInt()
        val sec = (TimeUnit.MILLISECONDS.toSeconds(timeMs) % TimeUnit.MINUTES.toSeconds(1)).toInt()
        val hourFormat = if (hours < 10) "0$hours" else "" + hours
        val minFormat = if (min < 10) "0$min" else "" + min
        val secFormat = if (sec < 10) "0$sec" else "" + sec
        time = if (showUnit) {
            when {
                hours != 0 -> context.getString(
                    R.string.hour,
                    hourFormat
                ) + " " + context.getString(
                    R.string.min,
                    minFormat
                ) + (if (showSeconds) " " + context.getString(R.string.sec, secFormat) else "")
                min != 0 -> context.getString(
                    R.string.min,
                    minFormat
                ) + (if (showSeconds) " " + context.getString(R.string.sec, secFormat) else "")
                else -> context.getString(R.string.sec, secFormat)
            }
        } else {
            if (hours != 0) hourFormat + ":" + minFormat + (if (showSeconds) ":$secFormat" else "") else if (min != 0) "00:" + minFormat + (if (showSeconds) ":$secFormat" else "") else "00:$secFormat"
        }
        return time
    }
}