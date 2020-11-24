package com.yang.common_lib.remote.di.component

import android.content.Context
import com.yang.common_lib.base.app.BaseApplication
import com.yang.common_lib.base.di.component.BaseComponent
import com.yang.common_lib.remote.di.module.RemoteModule
import com.yang.common_lib.scope.RemoteScope
import dagger.Component

@RemoteScope
@Component(modules = [RemoteModule::class],dependencies = [BaseComponent::class])
interface RemoteComponent{
    fun string(): String
    fun baseApplication(): BaseApplication
    fun context(): Context
}