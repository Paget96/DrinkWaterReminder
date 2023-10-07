package com.paget96.drinkwaterreminder.features.cup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paget96.drinkwaterreminder.data.PreferencesManager
import com.paget96.drinkwaterreminder.data.db.Cup
import com.paget96.drinkwaterreminder.data.db.CupType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val cups = listOf(
        Cup(0, CupType.Cup100ML, 100),
        Cup(1, CupType.Cup125ML, 125),
        Cup(2, CupType.Cup150L, 150),
        Cup(3, CupType.Cup175ML, 175),
        Cup(4, CupType.Cup200ML, 200),
        Cup(5, CupType.Cup300ML, 300),
        Cup(6, CupType.Cup400ML, 400),
        Cup(7, CupType.CupCustom, 0)
    )

    val selectedCup = preferencesManager.selectedCup.asLiveData()

    fun onSelectedCup(cupType: CupType) = viewModelScope.launch {
        preferencesManager.updateSelectedCup(cupType)
    }
}