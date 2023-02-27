package com.melody.map.myapplication.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.melody.map.myapplication.ui.DetailMapScreen
import com.melody.map.myapplication.utils.setComposable

fun NavGraphBuilder.addDetailMapGraph() {
    composable(
        route = "detail/ethnicity/{latitude}/{longitude}/{targetAssets}",
        arguments = listOf(
            navArgument("latitude") {
                type = NavType.FloatType
            },
            navArgument("longitude") {
                type = NavType.FloatType
            },
            navArgument("targetAssets") {
                type = NavType.StringType
            }
        )
    ) { entry ->
        DetailMapScreen(
            targetAssets = entry.arguments?.getString("targetAssets","")?: "",
            latitude = (entry.arguments?.getFloat("latitude")?:0F).toDouble(),
            longitude = (entry.arguments?.getFloat("longitude")?:0F).toDouble()
        )
    }
}