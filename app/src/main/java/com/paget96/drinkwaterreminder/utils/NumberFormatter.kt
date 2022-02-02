package com.paget96.drinkwaterreminder.utils

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class NumberFormatter {

    fun floatDecimalRound(inputNumber: Float, decimals: Int, allowBelowOne: Boolean): Float {
        return try {
            BigDecimal.valueOf(
                if (allowBelowOne) inputNumber.toDouble()
                else (if (inputNumber > 0 && inputNumber < 1) 1 else inputNumber).toDouble()
            ).setScale(decimals, RoundingMode.HALF_UP).toFloat()
        } catch (numberFormatException: NumberFormatException) {
            0f
        } catch (illegalArgumentException: IllegalArgumentException) {
            0f
        }
    }

    fun isNumberNegative(number: Long): Boolean {
        return number < 0
    }

    fun isNumberNegative(number: Int): Boolean {
        return number < 0
    }

    fun floatToInt(inputNumber: Float): Int {
        return inputNumber.roundToInt()
    }

    fun floatToLong(inputNumber: Float): Long {
        return inputNumber.roundToLong()
    }

    fun parseLongWithDefault(longToParse: String, defaultValue: Long): Long {
        return try {
            if (longToParse.trim { it <= ' ' } != "") longToParse.toLong()
            else defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun parseFloatWithDefault(floatToParse: String, defaultValue: Float): Float {
        return try {
            if (floatToParse.trim { it <= ' ' } != "") floatToParse.toFloat()
            else defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun parseDoubleWithDefault(doubleToParse: String, defaultValue: Double): Double {
        return try {
            if (doubleToParse.trim { it <= ' ' } != "") doubleToParse.toDouble()
            else defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }

    fun parseIntWithDefault(integerToParse: String, defaultValue: Int): Int {
        return try {
            if (integerToParse.trim { it <= ' ' } != "") integerToParse.toInt() else defaultValue
        } catch (nfe: NumberFormatException) {
            defaultValue
        }
    }
}