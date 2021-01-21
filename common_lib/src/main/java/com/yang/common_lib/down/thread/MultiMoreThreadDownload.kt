package com.yang.common_lib.down.thread

import android.util.Log
import com.yang.common_lib.util.showShort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL


/**
 * @ClassName MultiMoreThreadDownload
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/4 14:30
 */
class MultiMoreThreadDownload(var filePath: String, var fileUrl: String) : Thread() {


    var threadNum: Int = 10
    var fileSize: Int = 0
    var blockSize: Int = 0
    var fileDownloadMoreThreads = mutableListOf<FileDownloadMoreThread>()
    var downloadSize: Long = 0
    var downloadPercent: Int = 0
    var currentPercent: Int = 0
    var currentTimeMillis: Long = 0
    var usedTimeMillis: Int = 0
    var downloadSpeed: Int = 0
    var fileHasLength: Int = 0
    var finishDown = false

    companion object {
        private const val TAG = "MultiMoreThreadDownload"
    }

    override fun run() {

        try {
            val file = File(filePath)
            val url = URL(fileUrl)
            val openConnection = url.openConnection() as HttpURLConnection
            if (openConnection.responseCode == 200 || openConnection.responseCode == 206) {

                fileSize = openConnection.contentLength

                if (file.exists()) {
                    file.delete()
                    //fileHasLength = file.length().toInt()
                }

//                if (fileSize == fileHasLength) {
//                    return
//                }

                val randomAccessFile = RandomAccessFile(file, "rwd")

                //randomAccessFile.setLength(fileSize.toLong())

                randomAccessFile.close()

                blockSize = fileSize / threadNum

                Log.i(TAG, "run: $fileSize  $blockSize")

                for (i in 0 until threadNum) {

                    val fileDownloadMoreThread = FileDownloadMoreThread(
                        url,
                        file,
                        i * blockSize,
                        (i+1) * blockSize,
                        "线程$i"
                    )
                    fileDownloadMoreThreads.add(fileDownloadMoreThread)
                    fileDownloadMoreThread.start()
                }


                var finish = false

                val startTimeMillis = System.currentTimeMillis()

                while (!finish) {

                    finishDown = false
                    finish = true
                    downloadSize = 0
                    fileDownloadMoreThreads.forEach {
                        downloadSize += it.downloadSize
                        finish = it.finishDown
                    }

                    currentTimeMillis = System.currentTimeMillis()

                    usedTimeMillis = ((currentTimeMillis - startTimeMillis) / 1000).toInt()

                    if (usedTimeMillis == 0) {
                        usedTimeMillis = 1
                    }

                    downloadSpeed = ((downloadSize / usedTimeMillis) / 1024).toInt()

                    downloadPercent = (downloadSize * 100).toInt() / fileSize
                    if (currentPercent < downloadPercent) {
                        if (downloadPercent >= 99) {
                            Log.i(
                                TAG,
                                "run:  下载进度：$downloadPercent%  用时：$usedTimeMillis/s  下载速度：$downloadSpeed Kb/s"
                            )
                            downloadPercent = 100
                            finishDown = true
                        }
                        Log.i(
                            TAG,
                            "run:  下载进度：$downloadPercent%  用时：$usedTimeMillis/s  下载速度：$downloadSpeed Kb/s"
                        )
                        currentPercent = downloadPercent
                    }

                }

                GlobalScope.launch(Dispatchers.Main) {
                    showShort("下载完成：$filePath")
                }

            }

        } catch (e: Exception) {
            GlobalScope.launch(Dispatchers.Main) {
                showShort("下载失败：${e.message}")
            }
            Log.i(TAG, "run: ${e.message}")
        }


    }
}