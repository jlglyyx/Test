@file:JvmName("RxSchedulersUtil")

package com.yang.common_lib.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.tencent.bugly.proguard.t
import com.yang.common_lib.dialog.RemoteMessageDialog
import com.yang.common_lib.handle.ErrorHandle
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "RxSchedulersUtil"
private const val TIME_SHOW_START: Long = 300
private const val TIME_SHOW_END: Long = 500

var remoteMessageDialog: RemoteMessageDialog? = null


fun <T> io_main(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                ErrorHandle(it).handle()
                Log.i(TAG, "io_main: 请求失败${it.message}  ${ErrorHandle(it).handle()}")
            }
            .doOnNext {
                Log.i(TAG, "io_main: 请求成功 $it")
            }
    }

}

fun <T> io_main(context: Context): ObservableTransformer<T, T> {


    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (remoteMessageDialog == null) {
                    remoteMessageDialog = RemoteMessageDialog(context)
                    (context as FragmentActivity).lifecycle.addObserver(remoteMessageDialog!!)
                }
                if (!remoteMessageDialog!!.isShowing && !(context as Activity).isFinishing) {
                    remoteMessageDialog?.show()
                }
            }
            .doOnError {
                Log.i(TAG, "io_main: 请求失败${it.message} $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = ErrorHandle(it).handle()
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }
            }
            .doOnNext {
                Log.i(TAG, "io_main: 请求成功 $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = "请求成功"
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }

            }
    }
}

fun <T> io_main(context: Context, startContent: String): ObservableTransformer<T, T> {


    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (remoteMessageDialog == null) {
                    remoteMessageDialog = RemoteMessageDialog(context, startContent)
                    (context as FragmentActivity).lifecycle.addObserver(remoteMessageDialog!!)
                } else {
                    remoteMessageDialog?.content = startContent
                }
                if (!remoteMessageDialog!!.isShowing && !(context as Activity).isFinishing) {
                    remoteMessageDialog?.show()
                }

            }
            .doOnError {
                Log.i(TAG, "io_main: 请求失败${it.message} $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = ErrorHandle(it).handle()
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }

            }
            .doOnNext {
                Log.i(TAG, "io_main: 请求成功 $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = "请求成功"
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }

            }
    }
}


/**
 * @param context
 * @param startContent dialog请求开始文字
 * @param endContent dialog请求成功文字
 * */

fun <T> io_main(
    context: Context, startContent: String,
    endContent: String
): ObservableTransformer<T, T> {

    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (remoteMessageDialog == null) {
                    remoteMessageDialog = RemoteMessageDialog(context, startContent)
                    (context as FragmentActivity).lifecycle.addObserver(remoteMessageDialog!!)

                } else {
                    remoteMessageDialog?.content = startContent
                }
                if (!remoteMessageDialog!!.isShowing && !(context as Activity).isFinishing) {
                    remoteMessageDialog?.show()
                }

            }
            .doOnError {
                Log.i(TAG, "io_main: 请求失败${it.message} $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = ErrorHandle(it).handle()
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }

            }
            .doOnNext {
                Log.i(TAG, "io_main: 请求成功 $it")
                GlobalScope.launch(Dispatchers.Main) {
                    delay(TIME_SHOW_START)
                    remoteMessageDialog?.content = endContent
                    delay(TIME_SHOW_END)
                    if ((context as FragmentActivity).isFinishing){
                        return@launch
                    }
                    remoteMessageDialog?.dismiss()
                    remoteMessageDialog = null
                }

            }
    }
}




