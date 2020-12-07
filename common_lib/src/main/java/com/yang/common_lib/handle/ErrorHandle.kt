package com.yang.common_lib.handle

import com.yang.common_lib.R
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
class ErrorHandle(private val t:Throwable) {


    fun handle():String{
        return when(t){
            is HttpException -> {
                return when(t.code()){
                    HttpExceptionCode.NO_FIND.code -> {
                        HttpExceptionCode.NO_FIND.message
                    }
                    else -> {
                        "网络异常,稍后重试"
                    }
                }
            }
            is UnknownHostException -> {
                "网络未连接"
            }
            is SocketTimeoutException ->{
                "连接超时,稍后重试"
            }
            else -> {
                t.message.toString()
            }
        }




    }


}