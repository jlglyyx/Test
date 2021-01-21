package com.yang.module_video.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_video.di.module.VideoModule
import com.yang.module_video.ui.fragment.VideoFragment
import com.yang.module_video.ui.fragment.recommend.VideoRecommendFragment
import com.yang.module_video.ui.fragment.recommend.activity.UploadFileActivity
import com.yang.module_video.ui.fragment.recommend.activity.VideoPlayActivity
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
@Component(dependencies = [RemoteComponent::class],modules = [VideoModule::class])
interface VideoComponent {

    fun inject(videoFragment: VideoFragment)
    fun inject(videoRecommendFragment: VideoRecommendFragment)
    fun inject(videoPlayActivity: VideoPlayActivity)
    fun inject(uploadFileActivity: UploadFileActivity)

}