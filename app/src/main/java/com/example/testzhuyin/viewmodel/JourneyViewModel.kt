package com.example.testzhuyin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class JourneyViewModel : ViewModel() {

    var homeviewState by mutableStateOf(JourneyViewState())

}

data class JourneyViewState(
    var isNeedReload: Boolean = false
)

sealed class JourneyViewEvent{
    object onStartEvent : JourneyViewEvent()
    object onCollectionEvent : JourneyViewEvent()
    object onSetReLoad : JourneyViewEvent()
}