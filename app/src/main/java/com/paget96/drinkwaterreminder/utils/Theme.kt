package com.paget96.drinkwaterreminder.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.paget96.drinkwaterreminder.database.settings.SettingsDatabase

class Theme(context: Context?) {


    // Variables
    private val settingsDatabase = SettingsDatabase.getDatabase(context)
    private val numberFormatter = NumberFormatter()

    fun setDarkMode(darkMode: Int) {
        settingsDatabase!!.setSettingsState("dark_mode_v1", darkMode.toString())
        loadDarkMode(darkMode)
    }

    fun loadDarkMode(darkMode: Int) {
        AppCompatDelegate.setDefaultNightMode(darkMode)
    }

    fun getDarkMode(): Int {
        return numberFormatter.parseIntWithDefault(
            settingsDatabase!!.getSettingsState(
                "dark_mode_v1",
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM.toString()
            ),
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
    }

    /*fun setTheme(activity: Activity, theme: String) {
        settingsDatabase!!.setSettingsState("theme_v1", theme)
        loadTheme(activity, getThemeByTheName(theme))
    }*/

    fun loadTheme(activity: Activity, theme: Int) {
        activity.setTheme(theme)
    }

    /*fun getTheme(): Int {
        return getThemeByTheName(
            settingsDatabase!!.getSettingsState(
                "theme_v1",
                getThemeNameById(R.style.ThemeDefault)
            )
        )
    }*/

    /*fun getThemeByTheName(themeName: String): Int {
        return when (themeName) {
            "ThemeDefault" -> R.style.ThemeDefault
            "ThemeAmoled" -> R.style.ThemeAmoled
            "ThemePureAmoled" -> R.style.ThemePureAmoled
            else -> R.style.ThemeDefault
        }
    }

    fun getThemeNameById(themeName: Int?): String {
        return when (themeName) {
            R.style.ThemeDefault -> "ThemeDefault"
            R.style.ThemeAmoled -> "ThemeAmoled"
            R.style.ThemePureAmoled -> "ThemePureAmoled"
            else -> "ThemeDefault"
        }
    }*/
}