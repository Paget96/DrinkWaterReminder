package com.paget96.drinkwaterreminder.features.switchcup

import androidx.lifecycle.ViewModel
import com.paget96.drinkwaterreminder.recyclers.switchcup.SwitchCupData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SwitchCupViewModel @Inject constructor() : ViewModel() {

    val dummyList = mutableListOf(
        SwitchCupData(0, 100),
        SwitchCupData(1, 125),
        SwitchCupData(2, 150),
        SwitchCupData(3, 175),
        SwitchCupData(4, 200),
        SwitchCupData(5, 300),
        SwitchCupData(6, 400),
        SwitchCupData(-1, 0),
    )
}