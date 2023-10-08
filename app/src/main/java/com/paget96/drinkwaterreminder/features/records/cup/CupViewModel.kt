package com.paget96.drinkwaterreminder.features.records.cup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _cups: MutableLiveData<List<Cup>> = MutableLiveData(
        listOf(
            Cup(0, CupType.Cup100ML, 100.0F),
            Cup(1, CupType.Cup125ML, 125.0F),
            Cup(2, CupType.Cup150L, 150.0F),
            Cup(3, CupType.Cup175ML, 175.0F),
            Cup(4, CupType.Cup200ML, 200.0F),
            Cup(5, CupType.Cup300ML, 300.0F),
            Cup(6, CupType.Cup400ML, 400.0F),
            Cup(7, CupType.CupCustom, 0.0F),
            Cup(8, CupType.CupCustom, 0.0F)
        )
    )
    val cups: LiveData<List<Cup>> get() = _cups

    val selectedCup = preferencesManager.selectedCup.asLiveData()

    fun onSelectedCup(cupType: CupType) = viewModelScope.launch {
        preferencesManager.updateSelectedCup(cupType)
        _cupEventChannel.send(CupEvent.CupSelected)
    }

    sealed class CupEvent {
        data object CupSelected : CupEvent()
    }
}