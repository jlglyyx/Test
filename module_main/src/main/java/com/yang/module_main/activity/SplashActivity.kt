package com.yang.module_main.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding4.view.clicks
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.SPLASH_ACTIVITY
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_main.R
import com.yang.module_main.di.component.DaggerMainComponent
import com.yang.module_main.di.module.MainModule
import com.yang.module_main.factory.MainViewModelFactory
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @ClassName LoginActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/23 9:19
 */
@Route(path = SPLASH_ACTIVITY)
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var mainViewModel: MainViewModel

    var i = 1

    companion object {
        private const val TAG = "SplashActivity"
    }


    override fun getLayout(): Int {

        return R.layout.act_splash
    }

    override fun initView() {
        val launch = GlobalScope.launch(Dispatchers.Main) {
            repeat(i + 1) {
                tv_timer.text = "点击跳过${i--}秒"
                Log.i(TAG, "initView: $i")
                delay(1000)
            }
            ARouter.getInstance().build(RoutePath.LOGIN_ACTIVITY).navigation()
            finish()
        }
        tv_timer.clicks().throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe {
            launch.cancel()
            ARouter.getInstance().build(RoutePath.LOGIN_ACTIVITY).navigation()
            finish()
        }

        Glide.with(this).load(R.drawable.img_splash_bg).into(img_splash)

    }

    override fun initViewModel() {

        DaggerMainComponent.builder().mainModule(MainModule(this))
            .remoteComponent(getRemoteComponent()).build().inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

}