package com.yang.module_main.di.module

import android.content.Context
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_main.api.MainApiService
import com.yang.module_main.factory.MainViewModelFactory
import com.yang.module_main.repository.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.lang.ref.WeakReference


/**
 * @ClassName HomeModule
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:15
 */
@Module
class MainModule constructor(private val mActivity: Context) {


    @FragmentScope
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): MainApiService {
        return retrofit.create(MainApiService::class.java)
    }

    @FragmentScope
    @Provides
    fun provideHomeRepository(homeApiService:MainApiService): MainRepository = MainRepository(homeApiService)

    @FragmentScope
    @Provides
    fun provideHomeViewModelFactory(homeRepository:MainRepository): MainViewModelFactory {

        return MainViewModelFactory(homeRepository,WeakReference<Context>(mActivity).get()!!)
    }


}