package com.yang.module_mine.di.module
import android.content.Context
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_mine.api.MineApiService
import com.yang.module_mine.factory.MineViewModelFactory
import com.yang.module_mine.repository.MineRepository
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
class MineModule constructor(private val mActivity: Context) {

    @FragmentScope
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): MineApiService {
        return retrofit.create(MineApiService::class.java)
    }

    @FragmentScope
    @Provides
    fun provideHomeRepository(mineApiService: MineApiService): MineRepository = MineRepository(mineApiService)

    @FragmentScope
    @Provides
    fun provideHomeViewModelFactory(mineRepository:MineRepository): MineViewModelFactory {

        return MineViewModelFactory(mineRepository,WeakReference<Context>(mActivity).get()!!)
    }


}