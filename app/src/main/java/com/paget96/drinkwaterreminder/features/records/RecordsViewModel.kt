package com.paget96.drinkwaterreminder.features.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.paget96.drinkwaterreminder.data.db.WateringRecord
import com.paget96.drinkwaterreminder.data.db.WateringRecordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordsDao: WateringRecordDao
) : ViewModel() {

    private val _recordsEventChannel = Channel<RecordsEvent>()
    val recordsEvent get() = _recordsEventChannel.receiveAsFlow()

    val wateringRecords: LiveData<List<WateringRecord>>
        get() = recordsDao.wateringRecords().asLiveData()

    val waterLimit: LiveData<Float>
        get() = MutableLiveData(4000F)

    val maxProgress: LiveData<Float>
        get() = waterLimit.map {
            it + (it * 20f / 100f)
        }

    val amountOfWater: LiveData<Float>
        get() = recordsDao.amountOfWater().asLiveData()

    val waterLimitAndAmount: LiveData<Pair<Float, Float>>
        get() = combine(
            waterLimit.asFlow(),
            amountOfWater.asFlow()
        ) { limit, amount ->
            Pair(limit, amount)
        }.asLiveData()

    fun insertRecord(record: WateringRecord) = viewModelScope.launch {
        recordsDao.insert(record)
    }

    fun onUndoDeleteClick(record: WateringRecord) = viewModelScope.launch {
        recordsDao.insert(record)
    }

    fun onItemDelete(record: WateringRecord) = viewModelScope.launch {
        recordsDao.delete(record)
        _recordsEventChannel.send(RecordsEvent.ShowUndoDeleteRecordMessage(record))
    }

    fun onItemEdit(id: Long) = viewModelScope.launch {

    }

    sealed class RecordsEvent {
        data class ShowUndoDeleteRecordMessage(val record: WateringRecord) : RecordsEvent()
    }
}