package com.yang.common_lib.remote.di.response


/**
 * @ClassName Result
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/4 15:59
 */
data class MResult<T>(val data:T, val code:String, val message:String, val success:Boolean)
