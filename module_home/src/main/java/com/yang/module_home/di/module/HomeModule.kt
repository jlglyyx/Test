package com.yang.module_home.di.module

import android.content.Context
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_home.api.HomeApiService
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.repository.HomeRepository
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
class HomeModule constructor(private val mActivity: Context) {


    @FragmentScope
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

    @FragmentScope
    @Provides
    fun provideHomeRepository(homeApiService:HomeApiService): HomeRepository = HomeRepository(homeApiService)

    @FragmentScope
    @Provides
    fun provideHomeViewModelFactory(homeRepository:HomeRepository): HomeViewModelFactory {

        return HomeViewModelFactory(homeRepository,WeakReference<Context>(mActivity).get()!!)
    }


}