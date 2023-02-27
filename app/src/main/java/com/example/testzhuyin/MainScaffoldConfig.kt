package com.example.testzhuyin

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.testzhuyin.model.RouteKey
import com.example.testzhuyin.ui.components.BottomTabBar
import com.example.testzhuyin.ui.page.*
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.melody.map.myapplication.graph.addDetailMapGraph
import com.melody.map.myapplication.graph.addHomeMapGraph
import com.melody.map.myapplication.ui.DetailMapScreen
import com.melody.map.myapplication.ui.HomeMapScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScaffoldConfig(){

//    val navHostController = rememberNavController()
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
//    val scaffoldState = rememberScaffoldState()

    var selectIndex by remember{ mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = {
            when(currentDestination?.route){
                RouteKey.HOME.toString() -> BottomTabBar(navHostController = navController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.COMMUNITY.toString() -> BottomTabBar(navHostController = navController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.JOURNEY.toString() -> BottomTabBar(navHostController = navController, selectIndex = selectIndex){
                    selectIndex = it
                }
                RouteKey.MINE.toString() -> BottomTabBar(navHostController = navController, selectIndex = selectIndex){
                    selectIndex = it
                }
            }
        },
        content = {
            AnimatedNavHost(
                navController = navController,
                startDestination = RouteKey.HOME.toString(),
                modifier = Modifier.background(MaterialTheme.colors.background),

            ) {
                composable(route = RouteKey.HOME.toString()) {
                    HomePage(navController=navController)
                }

                composable(route = RouteKey.COMMUNITY.toString()) {
                    CommunityPage(navController = navController)
                }

                composable(route = RouteKey.JOURNEY.toString()) {
                    JourneyPage(navController = navController)
                }

                composable(route = RouteKey.MINE.toString()) {
                    MinePage(navController = navController)
                }

                composable(route="home_map"){
                    HomeMapScreen { latLng, targetAssets ->
                    navController.navigate(
                    route = "detail/ethnicity/${latLng.latitude}/${latLng.longitude}/${targetAssets}",
                    )
                    }
                }
                composable(route="detail/ethnicity/{latitude}/{longitude}/{targetAssets}", arguments = listOf(
                    navArgument("latitude") {
                        type = NavType.FloatType
                    },
                    navArgument("longitude") {
                        type = NavType.FloatType
                    },
                    navArgument("targetAssets") {
                        type = NavType.StringType
                    }
                ) ){entry ->
                    DetailMapScreen(
                        targetAssets = entry.arguments?.getString("targetAssets","")?: "",
                        latitude = (entry.arguments?.getFloat("latitude")?:0F).toDouble(),
                        longitude = (entry.arguments?.getFloat("longitude")?:0F).toDouble()
                    )
                }

            }
        },
    )
}