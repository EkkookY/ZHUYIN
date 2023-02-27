package com.example.testzhuyin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.testzhuyin.model.BottomTabBarRoute
import com.example.testzhuyin.ui.theme.green2
import com.example.testzhuyin.ui.theme.grey3
import com.example.testzhuyin.ui.theme.white


@Composable
fun BottomTabBar(navHostController: NavHostController, selectIndex:Int, onItemSelected:(position:Int)->Unit) {

    val tabBarList = listOf(
        BottomTabBarRoute.Home,
        BottomTabBarRoute.Community,
        BottomTabBarRoute.Journey,
        BottomTabBarRoute.Mine
    )

    BottomNavigation(
        modifier = Modifier.height(68.dp)
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        tabBarList.forEachIndexed { index,screen ->
            BottomNavigationItem(
                modifier = Modifier
                    .background(white)
                    .offset(y = 6.dp),
                icon = {
                    var iconRes = painterResource(id = screen.iconUnSelected)
                    if(selectIndex == index){
                        iconRes = painterResource(id = screen.iconSelected)
                    }
                    Image(painter = iconRes,
                        contentDescription = null,
                        modifier = Modifier.height(48.dp),
                    )
                },
                label = {
                    var textColor = grey3
                    if (selectIndex == index)
                        textColor = green2
                    Text(text = stringResource(screen.stringId),
                        fontSize = 12.sp,
                        color = textColor
                    )
                },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.key.toString()
                } == true,
                onClick = {
                    onItemSelected.invoke(index)
                    if (currentDestination?.route != screen.key.toString()) {
                        navHostController.navigate(screen.key.toString()) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                })
        }
    }

}