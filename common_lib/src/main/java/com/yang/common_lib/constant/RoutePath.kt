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

    //main fragment





    //home fragment
    const val HOME_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/HomeFragment"
    const val HOME_RECOMMEND_FRAGMENT = "/$MODULE_HOME/$FRAGMENT/RecommendFragment"




    const val MINE_FRAGMENT = "/$MODULE_MINE/$FRAGMENT/MineFragment"


}