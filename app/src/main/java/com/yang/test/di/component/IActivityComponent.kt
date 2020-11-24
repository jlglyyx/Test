package com.yang.test.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.ActivityScope
import com.yang.test.MainActivity
import com.yang.test.di.module.MActivityModule
import dagger.Component

@ActivityScope
@Component(modules = [MActivityModule::class],dependencies = [RemoteComponent::class])
interface IActivityComponent {

    fun inject(mainActivity: MainActivity)
}