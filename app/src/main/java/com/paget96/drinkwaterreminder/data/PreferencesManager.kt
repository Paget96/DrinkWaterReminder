package com.paget96.drinkwaterreminder.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.paget96.drinkwaterreminder.data.db.CupType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "selected_cup")

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val selectedCup: Flow<CupType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            CupType.valueOf(
                preferences[PreferencesKeys.SELECTED_CUP] ?: CupType.CupCustom.name
            )
        }

    suspend fun updateSelectedCup(cupType: CupType) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_CUP] = cupType.name
        }
    }

    private object PreferencesKeys {
        val SELECTED_CUP = stringPreferencesKey("selected_cup")
    }
}