package com.yang.module_picture.di.module

import android.content.Context
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_picture.api.PictureApiService
import com.yang.module_picture.factory.PictureViewModelFactory
import com.yang.module_picture.repository.PictureRepository
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
class PictureModule constructor(private val mActivity: Context) {


    @FragmentScope
    @Provides
    fun providePictureApiService(retrofit: Retrofit): PictureApiService {
        return retrofit.create(PictureApiService::class.java)
    }

    @FragmentScope
    @Provides
    fun providePictureRepository(pictureApiService:PictureApiService): PictureRepository = PictureRepository(pictureApiService)

    @FragmentScope
    @Provides
    fun providePictureViewModelFactory(pictureRepository:PictureRepository): PictureViewModelFactory {

        return PictureViewModelFactory(pictureRepository,WeakReference<Context>(mActivity).get()!!)
    }


}