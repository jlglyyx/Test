package com.yang.common_lib.startub

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import com.yang.common_lib.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StartupInitializer:Initializer<Unit> {

    companion object{
        private const val TAG = "StartupInitializer"
    }
    override fun create(context: Context) {
        Log.i(TAG, "create: 初始化")

        GlobalScope.launch (Dispatchers.Main){
            if (LeakCanary.isInAnalyzerProcess(context)) {
                return@launch
            }
            LeakCanary.install(context as Application?)
            CrashReport.initCrashReport(context, "7c70d54c13", true    )
//            if (BuildConfig.DEBUG) {
//                ARouter.openLog()     // 打印日志
//                ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//            }
//            ARouter.init(context) // 尽可能早，推荐在Application中初始化
        }

    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {

        return mutableListOf()
    }


}