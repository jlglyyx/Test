package com.yang.module_main.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_main.activity.LoginActivity
import com.yang.module_main.activity.MainActivity
import com.yang.module_main.activity.SplashActivity
import com.yang.module_main.di.module.MainModule
import dagger.Component


/**
 * @ClassName HomeComponent
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:16
 */
@FragmentScope
@Component(dependencies = [RemoteComponent::class],modules = [MainModule::class])
interface MainComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)

}