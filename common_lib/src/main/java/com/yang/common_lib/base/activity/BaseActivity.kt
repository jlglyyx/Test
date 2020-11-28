package com.yang.common_lib.base.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    companion object{
        private const val TAG = "BaseActivity"
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initViewModel()
        initView()
        //先强制竖屏吧 屏幕切换保活数据太难处理了
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun initViewModel()


}