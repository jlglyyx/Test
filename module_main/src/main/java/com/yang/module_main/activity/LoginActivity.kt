package com.yang.module_main.activity

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath.LOGIN_ACTIVITY
import com.yang.common_lib.constant.RoutePath.MAIN_ACTIVITY
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_main.R
import com.yang.module_main.di.component.DaggerMainComponent
import com.yang.module_main.di.module.MainModule
import com.yang.module_main.factory.MainViewModelFactory
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_login.*
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
@Route(path = LOGIN_ACTIVITY)
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var mainViewModel: MainViewModel


    private var i = -1

    override fun getLayout(): Int {

        return R.layout.act_login
    }

    override fun initView() {

        initVideoView()

        btn_login.setOnClickListener {
            checkForm()
        }
        ll_no_login.setOnClickListener {
            ARouter.getInstance().build(MAIN_ACTIVITY).navigation()
            finish()
        }
    }

    override fun initViewModel() {

        DaggerMainComponent.builder().mainModule(MainModule(this))
            .remoteComponent(getRemoteComponent()).build().inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    private fun login() {

        mainViewModel.login().observe(this, Observer {
            if (!TextUtils.equals(tv_user_name.text, it.userName)) {
                input_user_name.error = "账号不匹配"
                return@Observer
            }
            if (!TextUtils.equals(tv_password.text, it.password)) {
                input_password.error = "密码不匹配"
                return@Observer
            }
            if (++i >= 1) {
                return@Observer
            }
            Log.i("TAG", "login: ===dnknflskn")
            ARouter.getInstance().build(MAIN_ACTIVITY).navigation()
            finish()
        })

    }

    private fun checkForm() {
        if (TextUtils.isEmpty(tv_user_name.text)) {
            input_user_name.error = "请输入账号!"
            return
        }
        if (TextUtils.isEmpty(tv_password.text)) {
            input_password.error = "请输入密码"
            return
        }
        login()
    }


    private fun initVideoView(){
        //loginVideoView.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.splash}"))
        loginVideoView.setVideoPath("/data/user/0/com.yang.test/files/video/splash/splash.mp4")
        //loginVideoView.setVideoPath(Uri.parse("\"android.resource://\" + packageName + \"/\" + R.raw.splash"))
//        loginVideoView.setVideoPath("http://192.168.31.60:8080/files/splash.mp4")
        loginVideoView.setOnPreparedListener {
            it.setVolume(0f,0f)
            it.start()
        }
        loginVideoView.setOnCompletionListener {
            loginVideoView.start()
        }
    }

    override fun onRestart() {
        super.onRestart()
        initVideoView()
    }

    override fun onStop() {
        super.onStop()
        loginVideoView.stopPlayback()
    }

}