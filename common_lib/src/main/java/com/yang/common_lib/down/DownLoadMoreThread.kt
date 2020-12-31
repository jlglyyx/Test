package com.yang.common_lib.down

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.yang.common_lib.down.DownLoadListener
import kotlinx.coroutines.*
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


/**
 * @ClassName DownLoadThread
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/24 17:53
 */
class DownLoadMoreThread(builder: Builder) {
    private val isCanceled = false
    private val isPaused = false
    private var lastProcess: Int = 0
    private var executors = Executors.newFixedThreadPool(10)
    private var url: String? = null
    private var parentMkdirPath: String? = null
    private var childMkdirPath: String? = null
    private var fileName: String? = null
    private var suffix: String? = null
    private var downLoadListener: DownLoadListener? = null
    private var threadCount = 10
    private var haveNotification:Boolean
    private var urlType:String
    private lateinit var downFile:File

    init {
        url = builder.url
        parentMkdirPath = builder.parentMkdirPath
        childMkdirPath = builder.childMkdirPath
        fileName = builder.fileName
        suffix = builder.suffix
        downLoadListener = builder.downLoadListener
        threadCount = builder.threadCount
        haveNotification = builder.haveNotification
        urlType = builder.urlType
    }


    companion object {
        private const val TAG = "DownLoadThread"
        var countProcess = 0


    }

    /**
     * @param notificationId 通知id
     *
     * 下载
     * */
    fun startDownLoad(notificationId: Int) : File? {


        return runBlocking{
            val await = GlobalScope.async(Dispatchers.IO) {
                try {
                    val downLoadLength = getDownLoadLength(url!!)
                    if (downLoadLength == 0) {//获取文件长度为0
                        if (haveNotification) {
                            downLoadListener?.onFailed("文件不存在", notificationId)
                        }
                        return@async null
                    }
                    down(url!!, downLoadLength, suffix!!, notificationId)
                    downFile =
                        File("${urlType}/${parentMkdirPath}/${childMkdirPath}/${fileName}.${suffix}")
                    downFile
                } catch (e: Exception) {
                    if (haveNotification) {
                        downLoadListener?.onFailed("未知异常", notificationId)
                    }
                    null
                }
            }.await()
            await
        }

    }


    /**
     * @param url 下载路径
     *
     * @param downLoadLength 下载文件长度
     *
     * @param suffix 后缀名
     *
     * @param notificationId 通知Id
     *
     * 下载
     * */
    private fun down(url: String, downLoadLength: Int, suffix: String, notificationId: Int) {


        Log.i(TAG, "notificationId: $notificationId")
        var fileIndex: Long = 0
        val mkdirFile =
            File("${urlType}/${parentMkdirPath}/${childMkdirPath}")
        if (!mkdirFile.exists()) {//文件夹
            mkdirFile.mkdirs()
        }
        val file = File(mkdirFile, "${fileName}.${suffix}")
        if (file.exists()) {//文件
            fileIndex = file.length()
        }
        if (fileIndex == downLoadLength.toLong()) {
            if (haveNotification) {
                downLoadListener?.onSuccess("$url", notificationId)
            }
            return
        }
        val itemLength = downLoadLength / threadCount
        for (i in 0 until threadCount){
            MDownLoadThread(
                url,
                (i * itemLength).toLong(),
                ((i + 1) * itemLength).toLong(),
                file,
                i,
                downLoadListener!!,
                "线程$i"
            ).start()
        }
        GlobalScope.launch(Dispatchers.IO) {
            while (countProcess <= 100){
                Log.i(TAG, "ssssssssssss: $countProcess")
                if (haveNotification) {
                    downLoadListener?.onProgress(countProcess, notificationId)
                }
            }
            Log.i(TAG, "ssssssssssssa: $countProcess")
            if (haveNotification) {
                downLoadListener?.onSuccess(url, notificationId + 1)
                countProcess = 0
            }

        }

    }

    /**
     * @param url 下载路径
     *
     * 获取下载文件的长度
     * */
    private fun getDownLoadLength(url: String): Int {
        val mUrl = URL(url)
        var contentLength = 0
        val httpURLConnection = mUrl.openConnection() as HttpURLConnection
        if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 206) {
            contentLength = httpURLConnection.contentLength
            httpURLConnection.disconnect()
        }

        return contentLength
    }


    class Builder {
        var url: String? = null
        var parentMkdirPath: String? = null
        var childMkdirPath: String? = null
        var fileName: String? = null
        var suffix: String? = null
        var downLoadListener: DownLoadListener? = null
        var threadCount = 10
        var haveNotification:Boolean
        var urlType:String

        init {
            url = ""
            parentMkdirPath = "MFiles"
            childMkdirPath = ""
            fileName = "${System.currentTimeMillis()}"
            suffix = "jpg"
            threadCount = 10
            haveNotification = false
            urlType = Environment.getExternalStorageDirectory().toString()
        }

        fun url(url: String): Builder {
            if (TextUtils.isEmpty(url)) {
                return this
            }
            this.url = url
            return this
        }

        fun parentMkdirPath(parentMkdirPath: String): Builder {
            if (TextUtils.isEmpty(parentMkdirPath)) {
                return this
            }
            this.parentMkdirPath = parentMkdirPath
            return this
        }

        fun childMkdirPath(childMkdirPath: String): Builder {
            if (TextUtils.isEmpty(childMkdirPath)) {
                return this
            }
            this.childMkdirPath = childMkdirPath
            return this
        }

        fun fileName(fileName: String): Builder {
            if (TextUtils.isEmpty(fileName)) {
                return this
            }
            this.fileName = fileName
            return this
        }

        fun suffix(suffix: String): Builder {
            if (TextUtils.isEmpty(suffix)) {
                return this
            }
            this.suffix = suffix
            return this
        }

        fun downLoadListener(downLoadListener: DownLoadListener): Builder {
            this.downLoadListener = downLoadListener
            return this
        }
        fun threadCount(threadCount: Int): Builder {
            if (threadCount<=0){
                return this
            }
            this.threadCount = threadCount
            return this
        }

        fun haveNotification(haveNotification: Boolean): Builder {
            this.haveNotification = haveNotification
            return this
        }
        fun urlType(urlType: String): Builder {
            if (TextUtils.isEmpty(urlType)) {
                return this
            }
            this.urlType = urlType
            return this
        }

        fun build(): DownLoadMoreThread {
            return DownLoadMoreThread(this)
        }
    }


    class MDownLoadThread(
        var url: String,
        var startIndex: Long,
        var endIndex: Long,
        var file: File?,
        private var notificationId: Int,
        var downLoadListener: DownLoadListener,
        var threadId:String
    ) : Thread() {
        private var lastProcess = 0
        override fun run() {

            startDown()
        }
         @Synchronized fun startDown (){
            val mUrl = URL(url)
            val httpURLConnection = mUrl.openConnection() as HttpURLConnection
            httpURLConnection.setRequestProperty("Range", "bytes=$startIndex-$endIndex")
            if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 206) {
                val inputStream = httpURLConnection.inputStream
                val randomAccessFile = RandomAccessFile(file, "rw")
                randomAccessFile.seek(startIndex)

                val byteArrayOf = byteArrayOf(1024.toByte())
                var len: Int
                var total = 0
                while ((inputStream.read(byteArrayOf).also { len = it }) != -1) {
                    total += len
                    randomAccessFile.write(byteArrayOf, 0, len)
                    val process = ((total*100) / (endIndex - startIndex)).toInt()
                    if (process > lastProcess) {
                        lastProcess = process
                        countProcess++
                        //Log.i("hsdjakljlkjgok", "线程${threadId}下载了===:  ${process} ${countProcess}")
                    }
                }
            }else {
                downLoadListener.onFailed("连接异常", notificationId)
            }


        }

    }





}