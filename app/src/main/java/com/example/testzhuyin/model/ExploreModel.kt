package com.example.testzhuyin.data

import androidx.compose.runtime.Immutable

@Immutable
data class Articles(
    val name: String,
    val country: String,
    val latitude: String,
    val longitude: String
) {
    val nameToDisplay = "$name, $country"
}

@Immutable
data class ExploreModel(
    val city: Articles,
    val description: String,
    val imageUrl: String
)