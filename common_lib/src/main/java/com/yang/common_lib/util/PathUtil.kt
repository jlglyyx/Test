package com.yang.common_lib.util

import android.R.id
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import java.io.File


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
    Log.i("getAPath", "$uri   $documentId  ${uri.scheme}")
    when {

        "file".equals(uri.scheme, true) -> {

            path = uri.path.toString()

        }

        "content".equals(uri.scheme, true) -> {
            if ("com.tencent.mtt.fileprovider" == uri.authority) {
                val fileDir = Environment.getExternalStorageDirectory()
                val file = File(fileDir, path.substring("/QQBrowser".length, uri.path?.length!!))
                path = if (file.exists()) {
                    file.toString()
                } else {
                    ""
                }
            }
        }

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

                    if (!TextUtils.isEmpty(documentId)) {
                        path = if (documentId.startsWith("raw:")) {
                            documentId.replaceFirst("raw:", "")
                        } else {
                            try {
                                val contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"),
                                    documentId.toLong()
                                )
                                getDataColumn(context, contentUri, null, null)
                            } catch (e: NumberFormatException) {
                                ""
                            }
                        }

                    }

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
    val data = MediaStore.Images.Media._ID
    val arrayOf = arrayOf(data)
    val contentResolver = context.contentResolver
    val query = contentResolver.query(uri, arrayOf, selection, selectionArgs, null)

    val run = query?.run {
        moveToFirst()
        val columnIndexOrThrow = getColumnIndexOrThrow(data)
        Log.i("TAG", "getDataColumn: $columnIndexOrThrow")
        val path = getString(columnIndexOrThrow)
        close()
        path.toString()
    }
    return run.toString()
}


