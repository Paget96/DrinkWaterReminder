package com.paget96.drinkwaterreminder.features.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherViewModel @Inject constructor() : ViewModel() {

    private val _otherChannel = Channel<OtherEvent>()
    val otherEvent get() = _otherChannel.receiveAsFlow()

    fun navigateToSettingsScreen() = viewModelScope.launch {
        _otherChannel.send(OtherEvent.NavigateToSettingsScreen)
    }

    fun navigateToAboutScreen() = viewModelScope.launch {
        _otherChannel.send(OtherEvent.NavigateToAboutScreen)
    }

    sealed class OtherEvent {
        data object NavigateToSettingsScreen : OtherEvent()
        data object NavigateToAboutScreen : OtherEvent()
    }
}