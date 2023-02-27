package com.example.testzhuyin.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UserContent(
    val id:Int,
    val drawable: Int,
    val text: String,
    val user: Int,
    val name: String,
    val time: String,
    val Slocation: String,
    val Nationality: String,
)
