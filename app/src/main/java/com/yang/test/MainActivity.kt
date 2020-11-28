package com.yang.test

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.media.MediaScannerConnection
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.util.getRemoteComponent
import com.yang.test.viewmodel.RemoteViewModel
import com.yang.test.di.component.DaggerIActivityComponent
import com.yang.test.di.module.MActivityModule
import com.yang.test.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var remoteViewModel: RemoteViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun getLayout(): Int {
        return R.layout.activity_main

    }

    @SuppressLint("CheckResult")
    override fun initView() {

        tv_click.setOnClickListener {
            remoteViewModel.requestBD()
        }

        remoteViewModel.text.observe(this, Observer {
            tv_click.text = it
        })


        drawerLayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {
                Log.i(TAG, "onDrawerStateChanged: ==状态改变==$newState")
                
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                Log.i(TAG, "onDrawerStateChanged: ==状态改变==${slideOffset}")
                
            }

            override fun onDrawerClosed(drawerView: View) {

                Log.i(TAG, "onDrawerStateChanged: ==关闭")
            }

            override fun onDrawerOpened(drawerView: View) {
                Log.i(TAG, "onDrawerStateChanged: ==打开")
                
            }

        })
    }

    override fun initViewModel() {
        DaggerIActivityComponent.builder().mActivityModule(MActivityModule(this)).remoteComponent(getRemoteComponent()).build()
            .inject(this)
        remoteViewModel =
            ViewModelProvider(this, mainViewModelFactory).get(RemoteViewModel::class.java)
    }


}