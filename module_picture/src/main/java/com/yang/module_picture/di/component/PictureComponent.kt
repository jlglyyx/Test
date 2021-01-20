package com.yang.module_picture.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_picture.di.module.PictureModule
import com.yang.module_picture.ui.fragment.PictureFragment
import com.yang.module_picture.ui.fragment.RecommendFragment
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
@Component(dependencies = [RemoteComponent::class],modules = [PictureModule::class])
interface PictureComponent {

    fun inject(pictureFragment: PictureFragment)
    fun inject(recommendFragment: RecommendFragment)

}