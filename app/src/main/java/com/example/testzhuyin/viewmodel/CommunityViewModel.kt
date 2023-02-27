package com.example.testzhuyin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CommunityViewModel : ViewModel() {

    var homeviewState by mutableStateOf(CommunityViewState())

    data class CommunityViewState(
        var isNeedReload: Boolean = false
    )
}

