package com.yang.common_lib.handle

import retrofit2.HttpException
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
                        "网络异常"
                    }
                }
            }
            is UnknownHostException -> {
                "网络未连接"
            }
            else -> {
                "网络异常"
            }
        }




    }


}