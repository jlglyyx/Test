package com.yang.common_lib.base.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {

    lateinit var mContext: Context
    companion object{
        private const val TAG = "BaseActivity"
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        mContext = this
        initViewModel()
        initView()
        //先强制竖屏吧 屏幕切换保活数据太难处理了
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }


    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun initViewModel()


    fun <T: ViewModel> getViewModel(factory: ViewModelProvider.Factory, viewModel: Class<T>): T {

        return ViewModelProvider(this, factory).get(viewModel)
    }

    fun <T: ViewModel> getViewModel(viewModel: Class<T>): T {

        return ViewModelProvider(this).get(viewModel)
    }

}