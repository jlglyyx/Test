package com.yang.common_lib.down

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.yang.common_lib.R
import java.net.URL


/**
 * @ClassName DownLoadThread
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/24 17:53
 */
class DownLoadService : Service() {


    override fun onBind(intent: Intent?): IBinder {

        return DownloadBinder(this)

    }

    class DownloadBinder constructor(private val service: DownLoadService) : Binder(),
        DownLoadBinderListener {

        companion object{
            var i = 0
        }
        fun newBuilder(): StartBuilder {
            return StartBuilder(service)
        }

        class StartBuilder(private val service: DownLoadService) {

            private var url: String
            private var parentMkdirPath: String
            private var childMkdirPath: String
            private var fileName: String
            private var suffix: String



            init {
                url = ""
                parentMkdirPath = ""
                childMkdirPath = ""
                fileName = ""
                suffix = ""
            }

            fun url(url: String): StartBuilder {
                this.url = url
                return this
            }

            fun parentMkdirPath(parentMkdirPath: String): StartBuilder {
                this.parentMkdirPath = parentMkdirPath
                return this
            }

            fun childMkdirPath(childMkdirPath: String): StartBuilder {
                this.childMkdirPath = childMkdirPath
                return this
            }

            fun fileName(fileName: String): StartBuilder {
                this.fileName = fileName
                return this
            }

            fun suffix(suffix: String): StartBuilder {
                this.suffix = suffix
                return this
            }


            fun build() {
                DownLoadMoreThread.Builder()
                    .url(url)
                    .parentMkdirPath(parentMkdirPath)
                    .childMkdirPath(childMkdirPath)
                    .fileName(fileName)
                    .suffix(suffix)
                    .downLoadListener(DownLoadTask(service))
                    .threadCount(10)
                    .build().startDownLoad(i++)
            }

        }


    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createNotificationChannel("download", "下载通知", IMPORTANCE_HIGH)
        }
    }


    private fun createNotificationChannel(channelId: String, channelName: String, importance: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            getNotificationManager().createNotificationChannel(notificationChannel)
        } else {

        }
    }


    fun getNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


}