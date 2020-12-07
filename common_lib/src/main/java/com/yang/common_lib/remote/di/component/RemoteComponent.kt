package com.yang.common_lib.remote.di.component

import android.content.Context
import com.google.gson.Gson
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.base.app.BaseApplication
import com.yang.common_lib.base.di.component.BaseComponent
import com.yang.common_lib.remote.di.module.RemoteModule
import com.yang.common_lib.scope.RemoteScope
import dagger.Component
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@RemoteScope
@Component(modules = [RemoteModule::class],dependencies = [BaseComponent::class])
interface RemoteComponent{
    fun getBaseApplication(): BaseApplication
    fun getContext(): Context
    fun getRetrofit(): Retrofit
    fun getBaseApiService(): BaseApiService
    fun gson(): Gson
}