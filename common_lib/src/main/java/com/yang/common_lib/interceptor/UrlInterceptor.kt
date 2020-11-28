package com.yang.common_lib.interceptor

import android.text.TextUtils
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class UrlInterceptor: Interceptor {

    companion object{
        private const val TAG = "UrlInterceptor"
        var url:String? = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val encodedPath = request.url().encodedPath()
        if (url!=null || !TextUtils.isEmpty(url)){
            if (!url!!.endsWith("/")){
                throw Exception("url请以\"/\"结尾")
            }
            request = request.newBuilder().url(url+encodedPath.substring(1,encodedPath.length)).build()
            url = null //重置url
        }
        return chain.proceed(request)
    }

}