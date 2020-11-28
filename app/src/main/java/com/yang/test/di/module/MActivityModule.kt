package com.yang.test.di.module

import android.content.Context
import com.yang.common_lib.scope.ActivityScope
import com.yang.test.api.ApiService
import com.yang.test.factory.MainViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.lang.ref.WeakReference

@Module
class MActivityModule constructor(private val mActivity:Context) {

    @ActivityScope
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @ActivityScope
    @Provides
    fun provideMainViewModelFactory(apiService: ApiService): MainViewModelFactory {
        val mContext = WeakReference<Context>(mActivity)
        return MainViewModelFactory(apiService,mContext.get()!!)
    }
}