package com.yang.module_video.di.module

import android.content.Context
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_video.api.VideoApiService
import com.yang.module_video.factory.VideoViewModelFactory
import com.yang.module_video.repository.VideoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.lang.ref.WeakReference


/**
 * @ClassName VideoModule
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:15
 */
@Module
class VideoModule constructor(private val mActivity: Context) {


    @FragmentScope
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): VideoApiService {
        return retrofit.create(VideoApiService::class.java)
    }

    @FragmentScope
    @Provides
    fun provideHomeRepository(videoApiService:VideoApiService): VideoRepository = VideoRepository(videoApiService)

    @FragmentScope
    @Provides
    fun provideHomeViewModelFactory(videoRepository:VideoRepository): VideoViewModelFactory {

        return VideoViewModelFactory(videoRepository,WeakReference<Context>(mActivity).get()!!)
    }


}