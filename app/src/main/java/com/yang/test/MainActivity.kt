package com.yang.test

import android.content.Context
import android.util.Log
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.base.app.BaseApplication
import com.yang.common_lib.util.getRemoteComponent
import com.yang.test.di.component.DaggerIActivityComponent
import javax.inject.Inject

class MainActivity : BaseActivity() {


    @Inject
    lateinit var baseApplication: BaseApplication
    @Inject
    lateinit var a: Context
    @Inject
    lateinit var s: String


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        DaggerIActivityComponent.builder().remoteComponent(getRemoteComponent()).build().inject(this)
        Log.i("TAG", "initView: $baseApplication   ")
        Log.i("TAG", "initView: $a   ")
        Log.i("TAG", "initView: $s   ")
    }
}