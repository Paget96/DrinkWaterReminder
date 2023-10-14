package com.paget96.drinkwaterreminder.features.records.cup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle,
    private val selectedCupDao: SelectedCupDao
) : ViewModel() {

    private val _cupEventChannel = Channel<CupEvent>()
    val cupEvent get() = _cupEventChannel.receiveAsFlow()

    val selectedCup = selectedCupDao.cup().asLiveData()

    private val _cups: List<CupAdapter.Item> = listOf(
        CupAdapter.Item.CupItem(Cup.default),
        CupAdapter.Item.CupItem(Cup(CupType.Cup125ML, 125.0F, 2)),
        CupAdapter.Item.CupItem(Cup(CupType.Cup150L, 150.0F, 3)),
        CupAdapter.Item.CupItem(Cup(CupType.Cup175ML, 175.0F, 4)),
        CupAdapter.Item.CupItem(Cup(CupType.Cup200ML, 200.0F, 5)),
        CupAdapter.Item.CupItem(Cup(CupType.Cup300ML, 300.0F, 6)),
        CupAdapter.Item.CupItem(Cup(CupType.Cup400ML, 400.0F, 7)),
        CupAdapter.Item.CupItem(Cup(CupType.CupCustom, 0.0F, 8))
    )

    private val _cupsItem: MutableLiveData<List<CupAdapter.Item>> = MutableLiveData(
        _cups
    )
    val cupsItem: LiveData<List<CupAdapter.Item>> get() = _cupsItem

    fun resetCups() {
        _cupsItem.value = _cups
    }

    fun onSelectedCup(cup: SelectedCup) = viewModelScope.launch {
        if (cup.cup.cupType == CupType.CupCustom) {
            _cupsItem.value = _cups + CupAdapter.Item.CupInputItem
        } else {
            selectedCupDao.insert(cup)
            _cupEventChannel.send(CupEvent.CupSelected)
        }
    }

    sealed class CupEvent {
        data object CupSelected : CupEvent()
    }
}