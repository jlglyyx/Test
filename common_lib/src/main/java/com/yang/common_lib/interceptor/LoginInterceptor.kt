package com.yang.common_lib.interceptor

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor


/**
 * @ClassName LoginInterceptor
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/21 11:33
 */
@Interceptor(priority = 1, name = "登录状态拦截器")
class LoginInterceptor : IInterceptor {

    lateinit var mContext: Context

    companion object {
        private const val TAG = "LoginInterceptor"
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {


        Log.i(TAG, "process: 拦截器====$postcard=====")
        callback.onContinue(postcard)
    }

    override fun init(context: Context) {

        mContext = context
        //仅会调用一次
    }

}