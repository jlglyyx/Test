package com.yang.common_lib.constant


/**
 * @ClassName RoutePath
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 14:34
 */
object RoutePath {


    private const val ACTIVITY = "activity"
    private const val FRAGMENT = "fragment"
    private const val SERVICE = "service"
    private const val PROVIDE = "provide"

    private const val MODULE_MAIN = "module_main"
    private const val MODULE_HOME = "module_home"
    private const val MODULE_MINE = "module_mine"


    //main activity
    const val MAIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/MainActivity"
    const val LOGIN_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/LoginActivity"
    const val SPLASH_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/SplashActivity"
    const val REGISTER_ACTIVITY = "/$MODULE_MAIN/$ACTIVITY/RegisterActivity"

    //main fragment


    //home fragment
    const val HOME_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/HomeFragment"
    const val HOME_RECOMMEND_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/RecommendFragment"

    //home activity
    const val HOME_VIDEOPLAY_ACTIVITY = "/$MODULE_HOME/$ACTIVITY/VideoPlayActivity"
    const val HOME_SEARCH_ACTIVITY = "/$MODULE_HOME/$ACTIVITY/SearchActivity"

    const val MINE_FRAGMENT = "/$MODULE_MINE/$FRAGMENT/MineFragment"


}