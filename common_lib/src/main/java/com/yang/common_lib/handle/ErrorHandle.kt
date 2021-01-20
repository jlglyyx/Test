package com.yang.common_lib.handle

import android.util.Log
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * @ClassName ErrorHandle
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/26 18:01
 */
class ErrorHandle(private val t: Throwable) {

    companion object{
        private const val TAG = "ErrorHandle"
    }

    fun handle(): String {
        return when (t) {
            is HttpException -> {
                return when (t.code()) {
                    IHttpException.HttpException.NO_FIND.code -> {

                        IHttpException.HttpException.NO_FIND.message
                    }
                    else -> {

                        IHttpException.OtherException.NETWORK_ERROR.message
                    }
                }
            }
            is UnknownHostException -> {

                IHttpException.OtherException.NO_NETWORK_ERROR.message
            }
            is SocketTimeoutException -> {

                IHttpException.OtherException.NETWORK_ERROR.message
            }
            is JsonSyntaxException -> {

                IHttpException.OtherException.JSON_SYNTAX_ERROR.message
            }
            else -> {
                Log.i(TAG, "handle: ${t.message.toString()}")
                IHttpException.OtherException.UN_KNOWN_ERROR.message

            }
        }
    }


}