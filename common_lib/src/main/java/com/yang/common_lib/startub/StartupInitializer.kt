package com.yang.common_lib.startub

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import com.yang.common_lib.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartupInitializer:Initializer<Unit> {

    companion object{
        private const val TAG = "StartupInitializer"
    }
    override fun create(context: Context) {
        Log.i(TAG, "create: 初始化")

        GlobalScope.launch (Dispatchers.Main){
            delay(1000)
            if (LeakCanary.isInAnalyzerProcess(context)) {
                return@launch
            }
            LeakCanary.install(context as Application?)
            CrashReport.initCrashReport(context, "7c70d54c13", true)

        }

    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {

        return mutableListOf()
    }


}