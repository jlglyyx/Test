package com.yang.common_lib.base.di.component

import android.content.Context
import com.google.gson.Gson
import com.yang.common_lib.base.app.BaseApplication
import com.yang.common_lib.base.di.module.BaseModule
import com.yang.common_lib.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [BaseModule::class])
interface BaseComponent {
    fun baseApplication(): BaseApplication
    fun context(): Context
    fun gson(): Gson
}
