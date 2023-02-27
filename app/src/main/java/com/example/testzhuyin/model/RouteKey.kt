package com.example.testzhuyin.model


import androidx.annotation.StringRes
import androidx.navigation.NavHostController
import com.example.testzhuyin.R


enum class RouteKey {
    //一级界面
    HOME, // 首页
    COMMUNITY,//社区页
    JOURNEY,//旅途页
    MINE,//个人中心页

    LOGIN,//登录页

    //二级界面
    NAVI,//导航页
    COLLECTION,//收藏页
    NATIONALITY,//民族详细页
    UNITY,//虚拟小人详细页
    CHANGETHEMESTATE,//切换主题
    NOTE,//备忘录页

    //其他
    DIALOG,//弹窗
    DETAIL,//详情
}
object RouteNavigationUtil{

    fun navigationTo(
        navCtrl: NavHostController,
        destinationName: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true,
    ) {

        var singleArgument = ""
        if (args != null) {
            when (args) {
                is String -> {
                    singleArgument = String.format("/%s", args)
                }
                is Int -> {
                    singleArgument = String.format("/%s", args)
                }
                is Float -> {
                    singleArgument = String.format("/%s", args)
                }
                is Double -> {
                    singleArgument = String.format("/%s", args)
                }
                is Boolean -> {
                    singleArgument = String.format("/%s", args)
                }
                is Long -> {
                    singleArgument = String.format("/%s", args)
                }
            }
        }
        navCtrl.navigate("$destinationName$singleArgument") {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }

    fun NavHostController.back() {
        navigateUp()
    }
}

/**
 * @param key 路由key
 * @param stringId 导航栏显示的字符串
 * @param iconUnSelected 未选中显示的图标
 * @param iconSelected 选中显示的图标
 */
sealed class BottomTabBarRoute(
    var key: RouteKey, @StringRes var stringId:Int,
    var iconUnSelected: Int,
    var iconSelected: Int){

    object Home: BottomTabBarRoute(RouteKey.HOME, R.string.bottom_bar_popularize, R.drawable.icon_home_off, R.drawable.icon_home_on)
    object Community: BottomTabBarRoute(RouteKey.COMMUNITY, R.string.bottom_bar_community, R.drawable.icon_community_off, R.drawable.icon_community_on)
    object Journey: BottomTabBarRoute(RouteKey.JOURNEY, R.string.bottom_bar_journey, R.drawable.icon_journey_off, R.drawable.icon_journey_on)
    object Mine: BottomTabBarRoute(RouteKey.MINE, R.string.bottom_bar_mine, R.drawable.icon_mine_off, R.drawable.icon_mine_on)

}