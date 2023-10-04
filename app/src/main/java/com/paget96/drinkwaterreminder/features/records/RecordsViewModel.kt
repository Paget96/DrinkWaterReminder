package com.paget96.drinkwaterreminder.features.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.paget96.drinkwaterreminder.data.db.WateringRecord
import com.paget96.drinkwaterreminder.data.db.WateringRecordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val recordsDao: WateringRecordDao
) : ViewModel() {

    val wateringRecords = recordsDao.wateringRecords().asLiveData()

    fun insertRecord(record: WateringRecord) = viewModelScope.launch {
        recordsDao.insert(record)
    }
}