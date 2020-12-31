package com.yang.common_lib.util

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import java.io.*


/**
 * @ClassName PathUtil
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 16:25
 */

fun getPath(context: Context, uri: Uri): String {
    val documentId = DocumentsContract.getDocumentId(uri)
    val split = documentId.split(":")
    var path = ""
    Log.i("getAPath", "$uri   $documentId")
    when {
        DocumentsContract.isDocumentUri(context, uri) -> {
            when (uri.authority) {
                "com.android.externalstorage.documents" -> {
                    if (split.size >= 2) {
                        val type = split[0]
                        if ("primary".equals(type, true)) {
                            path = "${Environment.getExternalStorageDirectory()}/${split[1]}"
                        }
                    }
                }
                "com.android.providers.downloads.documents" -> {

                    //path = getFilePathFromURI(context, uri)
                    //Log.i("assssssd", "aaaaaaaaaaa: $path")

//                        val arrayOf = arrayOf(
//                            "content://downloads/my_downloads",
//                            "content://downloads/all_downloads",
//                            "content://downloads/public_downloads"
//                        )
//                        arrayOf.forEach {
//                            val contentUri = ContentUris.withAppendedId(
//                                Uri.parse(it),
//                                documentId.toLong()
//                            )
//                            try {
//                                val dataColumn = getDataColumn(context, contentUri, null, null)
//                                if (dataColumn != "") {
//                                    Log.i("sadssssss", "getAPath: $dataColumn")
//                                    path = dataColumn
//                                }
//                            } catch (e: Exception) {
//                                Log.i("Exception", ": ${e.message}")
//                            }
//
//                        }


                }
                "com.android.providers.media.documents" -> {
                    if (split.size >= 2) {
                        val contentUri = when (split[0]) {
                            "image" -> {
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            }
                            "video" -> {
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            }
                            "audio" -> {
                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            }

                            else -> {
                                null
                            }

                        }

                        if (contentUri != null) {
                            val selection = MediaStore.Images.Media._ID + "=?"
                            path =
                                getDataColumn(context, contentUri, selection, arrayOf(split[1]))
                        }
                    }
                }
            }

        }
        "file".equals(uri.scheme, true) -> {

            path = uri.path.toString()

        }
        else -> {
            path = getDataColumn(context, uri, null, null)
        }
    }

    return path

}


@SuppressLint("Recycle")
fun getDataColumn(
    context: Context,
    uri: Uri,
    selection: String?,
    selectionArgs: Array<String>?
): String {
    Log.i("getAPath", "getAPathuri: $uri")
    val data = MediaStore.MediaColumns._ID
    val arrayOf = arrayOf(data)
    val contentResolver = context.contentResolver
    val query = contentResolver.query(uri, arrayOf, selection, selectionArgs, null)
    Log.i("TAG", "getDataColumn: ${query?.moveToFirst() == null}")
    if (query?.moveToFirst()!!) {
        val columnIndexOrThrow = query.getColumnIndexOrThrow(data)
        val path = columnIndexOrThrow.let { query.getString(it) }
        query.close()
        return path.toString()
    }
    return ""
}


