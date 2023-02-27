package com.melody.map.myapplication.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.melody.map.myapplication.ui.HomeMapScreen
import com.melody.map.myapplication.utils.setComposable

fun NavGraphBuilder.addHomeMapGraph(navController: NavHostController) {
    //【有动画位移】,移动的时候会掉帧，自己选择
    /*setComposable(route = "home_map") {
        HomeMapScreen { latLng, targetAssets ->
            navController.navigate(
                route = "detail/ethnicity/${latLng.latitude}/${latLng.longitude}/${targetAssets}",
            )
        }
    }*/

    // 【默认的透明显示和隐藏】
//    composable(route = "home_map") {
//        HomeMapScreen { latLng, targetAssets ->
//            navController.navigate(
//                route = "detail/ethnicity/${latLng.latitude}/${latLng.longitude}/${targetAssets}",
//            )
//        }
//    }
}