package com.paget96.drinkwaterreminder.features.records.cup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paget96.drinkwaterreminder.data.PreferencesManager
import com.paget96.drinkwaterreminder.data.db.Cup
import com.paget96.drinkwaterreminder.data.db.CupType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _cupEventChannel = Channel<CupEvent>()
    val cupEvent get() = _cupEventChannel.receiveAsFlow()

    val cups = listOf(
        Cup(0, CupType.Cup100ML),
        Cup(1, CupType.Cup125ML),
        Cup(2, CupType.Cup150L),
        Cup(3, CupType.Cup175ML),
        Cup(4, CupType.Cup200ML),
        Cup(5, CupType.Cup300ML),
        Cup(6, CupType.Cup400ML),
        Cup(7, CupType.CupCustom)
    )

    val selectedCup = preferencesManager.selectedCup.asLiveData()

    fun onSelectedCup(cupType: CupType) = viewModelScope.launch {
        preferencesManager.updateSelectedCup(cupType)
        _cupEventChannel.send(CupEvent.CupSelected)
    }

    sealed class CupEvent {
        data object CupSelected : CupEvent()
    }
}