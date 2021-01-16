package com.yang.module_main.activity

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.clicks
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_main.R
import com.yang.module_main.data.UserBean
import com.yang.module_main.di.component.DaggerMainComponent
import com.yang.module_main.di.module.MainModule
import com.yang.module_main.factory.MainViewModelFactory
import com.yang.module_main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.act_register.*
import javax.inject.Inject


/**
 * @ClassName RegisterActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/12 15:05
 */
@Route(path = RoutePath.REGISTER_ACTIVITY)
class RegisterActivity:BaseActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var mainViewModel: MainViewModel

    override fun getLayout(): Int {

        return R.layout.act_register
    }

    override fun initView() {

        clicks(btn_register).subscribe {

            register()
        }



    }

    override fun initViewModel() {
        DaggerMainComponent.builder().mainModule(MainModule(this))
        .remoteComponent(getRemoteComponent()).build().inject(this)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

    }


    private fun register(){

        if (TextUtils.isEmpty(tv_user_name.text)){
            input_user_name.error = "用户名为空"
            return
        }
        if (TextUtils.isEmpty(tv_password.text)){
            input_password.error = "密码为空"
            return
        }

        val userBean = UserBean(
            null,
            0,
            0,
            null,
            null,
            tv_user_name.text.toString(),
            "${Math.random()*1000}",
            tv_password.text.toString()
        )
        mainViewModel.register(userBean).observe(this, Observer {
            if (it.success){
                finish()
            }else{
                input_user_name.error = it.message
            }

        })

    }
}