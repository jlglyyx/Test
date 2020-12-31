package com.yang.common_lib.down

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.yang.common_lib.R
import com.yang.common_lib.down.DownLoadListener
import com.yang.common_lib.down.DownLoadService


/**
 * @ClassName DownLoad
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/25 9:15
 */
class DownLoadTask constructor(private val service: DownLoadService) :
    DownLoadListener {

    companion object {
        private const val TAG = "DownLoadTask"
    }

    override fun onSuccess(downloadPath: String,notificationId:Int) {

        Log.i(TAG, "onSuccess: $downloadPath")

        getNotificationManager().notify(notificationId,sendNotification(downloadPath,"下载完成"))

    }

    override fun onFailed(errorMessage: String,notificationId:Int) {

        getNotificationManager().notify(notificationId,sendNotification("懒得去管为什么，反正就是下载失败了($notificationId)","下载失败"))
    }

    override fun onPaused() {
    }

    override fun onCanceled() {
    }

    override fun onProgress(progress: Int,notificationId:Int) {
        Log.i(TAG, "onProgress: $progress")
        getNotificationManager().notify(notificationId,sendNotification(progress,"正在下载"))
        if (progress>=100){
            getNotificationManager().cancel(notificationId)
        }

    }


    private fun sendNotification(contentText:String, contentTitle:String): Notification {
        return NotificationCompat.Builder(service,"download")
            .setContentText(contentText)
            .setContentTitle(contentTitle)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.img_launcher)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

    }
    private fun sendNotification(progress:Int, contentTitle:String): Notification {
        return NotificationCompat.Builder(service,"download")
            .setContentText("$progress%")
            .setContentTitle(contentTitle)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.img_launcher)
            .setAutoCancel(true)
            .setProgress(100,progress,false)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .build()

    }

    fun getNotificationManager(): NotificationManager {
        return service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

}