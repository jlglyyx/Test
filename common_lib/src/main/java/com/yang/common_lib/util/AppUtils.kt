@file:JvmName("AppUtils")
package com.yang.common_lib.util
import android.content.Context


/*
* @return 宽高集合
**/
fun getScreenPx(context: Context):ArrayList<Int>{
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val widthPixels = displayMetrics.widthPixels
    val heightPixels = displayMetrics.heightPixels
    return arrayListOf(widthPixels,heightPixels)
}
/*
* @return 宽高集合
**/
fun getScreenDpi(context: Context):ArrayList<Float>{
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val widthPixels = displayMetrics.xdpi
    val heightPixels = displayMetrics.ydpi
    return arrayListOf(widthPixels,heightPixels)
}
