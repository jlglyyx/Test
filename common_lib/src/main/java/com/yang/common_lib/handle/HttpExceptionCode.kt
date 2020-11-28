package com.yang.common_lib.handle


/**
 * @ClassName ErrorCode
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/26 17:57
 */
enum class HttpExceptionCode(val code:Int, val message:String){
    NO_FIND(404,"资源未找到")

}