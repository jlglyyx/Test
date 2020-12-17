package com.yang.module_home.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.ui.fragment.HomeFragment
import com.yang.module_home.ui.fragment.recommend.RecommendFragment
import com.yang.module_home.ui.fragment.recommend.activity.VideoPlayActivity
import dagger.Component


/**
 * @ClassName HomeComponent
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:16
 */
@FragmentScope
@Component(dependencies = [RemoteComponent::class],modules = [HomeModule::class])
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)
    fun inject(recommendFragment: RecommendFragment)
    fun inject(videoPlayActivity: VideoPlayActivity)

}