package com.yang.common_lib.base.app

import android.app.Application
import android.content.Context
import androidx.startup.AppInitializer
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import com.yang.common_lib.BuildConfig
import com.yang.common_lib.startub.StartupInitializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BaseApplication : Application() {

    companion object {
        private lateinit var mApplicationContext:Context
        private lateinit var mBaseApplication:BaseApplication

        val baseApplication: BaseApplication by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            mBaseApplication
        }
        val applicationContext: Context by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            mApplicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mBaseApplication = this
        AppInitializer.getInstance(this).initializeComponent(StartupInitializer::class.java)
        GlobalScope.launch(Dispatchers.Unconfined) {
            if (BuildConfig.DEBUG) {
                ARouter.openLog()     // 打印日志
                ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(mBaseApplication) // 尽可能早，推荐在Application中初始化
        }

        GlobalScope.launch {
            delay(500)
            Glide.get(mBaseApplication)
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mApplicationContext = baseContext
    }
}