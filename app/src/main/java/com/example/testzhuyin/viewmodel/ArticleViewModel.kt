package com.example.testzhuyin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ArticleViewModel:ViewModel() {

    var ArticleviewState by mutableStateOf(ArticleViewState())

    data class ArticleViewState(
        var isNeedReload: Boolean = false
    )

    sealed class ArticleViewEvent{
        object onStartEvent : ArticleViewEvent()
        object onCollectionEvent : ArticleViewEvent()
        object onSetReLoad : ArticleViewEvent()
    }
}