package com.yang.common_lib.down


/**
 * @ClassName DownListener
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/24 17:52
 */
interface DownLoadListener {

    fun onSuccess(downloadPath:String,notificationId:Int)

    fun onFailed(errorMessage:String,notificationId:Int)

    fun onPaused()

    fun onCanceled()

    fun onProgress(progress:Int,notificationId:Int)
}