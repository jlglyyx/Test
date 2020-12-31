package com.yang.common_lib.down

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.yang.common_lib.down.DownLoadListener
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
class DownLoadThread(builder: Builder) {
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

    init {
        url = builder.url
        parentMkdirPath = builder.parentMkdirPath
        childMkdirPath = builder.childMkdirPath
        fileName = builder.fileName
        suffix = builder.suffix
        downLoadListener = builder.downLoadListener
    }


    companion object {
        private const val TAG = "DownLoadThread"
    }

    /**
     * @param notificationId 通知id
     *
     * 下载
     * */
    fun startDownLoad(notificationId: Int) {
        executors.execute {
            try {
                val downLoadLength = getDownLoadLength(url!!)
                if (downLoadLength == 0) {//获取文件长度为0
                    downLoadListener?.onFailed("文件不存在", notificationId)
                    return@execute
                }
                down(url!!, downLoadLength, suffix!!, notificationId)
            } catch (e: Exception) {
                downLoadListener?.onFailed("未知异常", notificationId)
            }

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

        var fileIndex: Long = 0

        val mkdirFile =
            File("${Environment.getExternalStorageDirectory()}/${parentMkdirPath}/${childMkdirPath}")
        if (!mkdirFile.exists()) {//文件夹
            mkdirFile.mkdirs()
        }
        val file = File(mkdirFile, "${fileName}.${suffix}")
        if (file.exists()) {//文件
            fileIndex = file.length()
        }

        if (fileIndex == downLoadLength.toLong()) {
            downLoadListener?.onSuccess("$url", notificationId)
            return
        }
        val mUrl = URL(url)
        val httpURLConnection = mUrl.openConnection() as HttpURLConnection
        if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 206) {
            val inputStream = httpURLConnection.inputStream
            val randomAccessFile = RandomAccessFile(file, "rw")
            randomAccessFile.seek(fileIndex)

            val byteArrayOf = byteArrayOf(1024.toByte())
            var len: Int
            var total = 0
            while ((inputStream.read(byteArrayOf).also { len = it }) != -1) {

                when {
                    isCanceled -> downLoadListener?.onCanceled()
                    isPaused -> downLoadListener?.onPaused()
                    else -> {
                        total += len
                        randomAccessFile.write(byteArrayOf, 0, len)

                        val process = (((total + fileIndex) * 100) / downLoadLength).toInt()
                        Log.i(TAG, "down: $process  $lastProcess")
                        Log.i("shkajhfkjah", "线程下载了: ${process}   ${total}  ${len}  ${fileIndex}  ${downLoadLength}")
                        if (process > lastProcess) {
                            lastProcess = process
                            Log.i(TAG, "down: $process  $lastProcess")
                            downLoadListener?.onProgress(lastProcess, notificationId)
                        }
                    }
                }

            }

            downLoadListener?.onSuccess(url, notificationId)
        } else {
            downLoadListener?.onFailed("连接异常", notificationId)
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

        init {
            url = ""
            parentMkdirPath = "MFiles"
            childMkdirPath = ""
            fileName = "${System.currentTimeMillis()}"
            suffix = "jpg"
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

        fun build(): DownLoadThread {
            return DownLoadThread(this)
        }
    }


}