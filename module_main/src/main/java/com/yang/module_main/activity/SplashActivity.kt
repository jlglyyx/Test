package com.yang.module_main.activity

import android.app.ActivityOptions
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Environment
import android.os.IBinder
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
import com.yang.common_lib.down.DownLoadService
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
import java.io.File
import java.io.RandomAccessFile
import java.util.RandomAccess
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

    lateinit var serviceConnection: ServiceConnection
    lateinit var downLoadService: DownLoadService.DownloadBinder

    var i = 1

    companion object {
        private const val TAG = "SplashActivity"
    }


    override fun getLayout(): Int {

        return R.layout.act_splash
    }

    override fun initView() {
        initDownLoadService()
        val launch = GlobalScope.launch(Dispatchers.Main) {
            repeat(i + 1) {
                tv_timer.text = "点击跳过${i--}秒"
                Log.i(TAG, "initView: $i")
                delay(1000)
            }
            ARouter.getInstance().build(RoutePath.LOGIN_ACTIVITY)
                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out).navigation()
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        tv_timer.clicks().throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe {
            launch.cancel()
            ARouter.getInstance().build(RoutePath.LOGIN_ACTIVITY)
                .withTransition(android.R.anim.fade_in, android.R.anim.fade_out).navigation()
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        Glide.with(this).load(R.drawable.img_splash_bg).into(img_splash)

    }

    override fun initViewModel() {

        DaggerMainComponent.builder().mainModule(MainModule(this))
            .remoteComponent(getRemoteComponent()).build().inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }


    private fun initDownLoadService() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i(TAG, "onServiceDisconnected: ")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                downLoadService = service as DownLoadService.DownloadBinder
                GlobalScope.launch {
                    downFile()
                }
            }

        }
        val intent = Intent(this, DownLoadService::class.java)

        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        startService(intent)

    }




    private fun downFile() {

        val file = downLoadService.newBuilder().urlType(filesDir.toString())
            .parentMkdirPath("video")
            .childMkdirPath("splash")
            .url("http://192.168.31.60:8080/files/splash.mp4")
            .fileName("splash")
            .suffix("mp4")
            .threadCount(20)
            .build()

        Log.i(TAG, "initDownLoadService: ${file?.absolutePath}")

    }

}