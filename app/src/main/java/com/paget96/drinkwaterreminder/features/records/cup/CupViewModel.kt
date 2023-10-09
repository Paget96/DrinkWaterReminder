package com.paget96.drinkwaterreminder.features.records.cup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paget96.drinkwaterreminder.data.db.Cup
import com.paget96.drinkwaterreminder.data.db.CupType
import com.paget96.drinkwaterreminder.data.db.SelectedCup
import com.paget96.drinkwaterreminder.data.db.SelectedCupDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CupViewModel @Inject constructor(
    private val selectedCupDao: SelectedCupDao
) : ViewModel() {

    private val _cupEventChannel = Channel<CupEvent>()
    val cupEvent get() = _cupEventChannel.receiveAsFlow()

    private val _cups: MutableLiveData<List<Cup>> = MutableLiveData(
        listOf(
            Cup.default,
            Cup(CupType.Cup125ML, 125.0F, 2),
            Cup(CupType.Cup150L, 150.0F, 3),
            Cup(CupType.Cup175ML, 175.0F, 4),
            Cup(CupType.Cup200ML, 200.0F, 5),
            Cup(CupType.Cup300ML, 300.0F, 6),
            Cup(CupType.Cup400ML, 400.0F, 7),
            Cup(CupType.CupCustom, 0.0F, 8)
        )
    )
    val cups: LiveData<List<Cup>> get() = _cups

    val selectedCup = selectedCupDao.cup().asLiveData()

    fun onSelectedCup(cup: SelectedCup) = viewModelScope.launch {
        selectedCupDao.insert(cup)
        _cupEventChannel.send(CupEvent.CupSelected)
    }

    sealed class CupEvent {
        data object CupSelected : CupEvent()
    }
}