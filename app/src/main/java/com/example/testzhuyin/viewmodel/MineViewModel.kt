package com.example.testzhuyin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.ViewModel


class MineViewModel  : ViewModel() {
    var mineviewState by mutableStateOf(MineViewState())


}

data class MineViewState(
    var cardOffset: IntOffset = IntOffset(0, 0)
)

sealed class MineViewEvent{
    object onChangeCurrentBanner : MineViewEvent()
    object onChangeCardSize : MineViewEvent()
    object onChangeFullSize : MineViewEvent()
    object onChangeCardOffset : MineViewEvent()
}