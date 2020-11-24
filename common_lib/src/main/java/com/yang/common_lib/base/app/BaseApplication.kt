package com.yang.common_lib.base.app

import android.app.Application
import android.content.Context

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
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        mApplicationContext = baseContext
    }
}