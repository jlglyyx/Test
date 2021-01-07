package com.yang.common_lib.down

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.lifecycle.Observer
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.constant.Constant
import com.yang.common_lib.down.DownLoadTask
import com.yang.common_lib.down.thread.MultiMoreThreadDownload
import com.yang.common_lib.interceptor.UrlInterceptor
import java.io.File


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

    companion object{
        private const val TAG = "DownLoadService"
    }


    override fun onBind(intent: Intent?): IBinder {

        return DownloadBinder(
            this
        )

    }

    class DownloadBinder constructor(private val service: DownLoadService) : Binder(),
        DownLoadBinderListener {

        companion object{
            var i = 0
        }
        fun newBuilder(): StartBuilder {
            return StartBuilder(
                service
            )
        }

        class StartBuilder(private val service: DownLoadService) {

            private var url: String
            private var parentMkdirPath: String
            private var childMkdirPath: String
            private var fileName: String
            private var suffix: String
            private var haveNotification:Boolean
            private var urlType:String
            private var threadCount:Int


            init {
                url = ""
                parentMkdirPath = ""
                childMkdirPath = ""
                fileName = ""
                suffix = ""
                haveNotification = false
                urlType = Environment.getExternalStorageDirectory().toString()
                threadCount = 10
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
            fun haveNotification(haveNotification: Boolean): StartBuilder {
                this.haveNotification = haveNotification
                return this
            }
            fun urlType(urlType: String): StartBuilder {
                this.urlType = urlType
                return this
            }
            fun threadCount(threadCount: Int): StartBuilder {
                this.threadCount = threadCount
                return this
            }


            fun build() : File?{
               return DownLoadMoreThread.Builder()
                    .url(url)
                    .parentMkdirPath(parentMkdirPath)
                    .childMkdirPath(childMkdirPath)
                    .fileName(fileName)
                    .suffix(suffix)
                    .haveNotification(false)
                    .downLoadListener(
                        DownLoadTask(
                            service
                        )
                    )
                    .urlType(urlType)
                    .threadCount(threadCount)
                    .build().startDownLoad(i++)
            }

        }


        interface SplashVideo{
            fun splashVideo()
        }

        var splashVideo:SplashVideo? = null


        fun splashVideo(){

            splashVideo?.splashVideo()
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: 连接成功")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: 销毁")
    }
}