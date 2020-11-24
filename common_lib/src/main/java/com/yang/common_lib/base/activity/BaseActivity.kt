package com.yang.common_lib.base.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

open abstract class BaseActivity: AppCompatActivity() {

    companion object{
        private const val TAG = "BaseActivity"
    }
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initView()
    }


    abstract fun getLayout():Int
    abstract fun initView()
}