@file:JvmName("ToastShowUtil")

package com.yang.common_lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.yang.common_lib.base.app.BaseApplication


/**
 * @ClassName ToastShowUtil
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/2 9:38
 */
private var toast: Toast? = null
private val context: Context = BaseApplication.applicationContext

@SuppressLint("ShowToast")
fun showShort(msg: Any) {
    if (toast == null) {
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT)
        toast?.setText(msg.toString())
    } else {
        toast!!.setText(msg.toString())
        toast!!.duration = Toast.LENGTH_SHORT
    }
    toast?.show()
}

@SuppressLint("ShowToast")
fun showLong(msg: Any) {
    if (toast == null) {
        toast = Toast.makeText(context, null, Toast.LENGTH_LONG)
        toast?.setText(msg.toString())
    } else {
        toast!!.setText(msg.toString())
        toast!!.duration = Toast.LENGTH_LONG
    }
    toast?.show()
}