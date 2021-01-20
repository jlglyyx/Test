package com.yang.module_mine.di.component

import com.yang.common_lib.remote.di.component.RemoteComponent
import com.yang.common_lib.scope.FragmentScope
import com.yang.module_mine.di.module.MineModule
import com.yang.module_mine.fragment.MineFragment
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
@Component(dependencies = [RemoteComponent::class],modules = [MineModule::class])
interface MineComponent {
    fun inject(mineFragment: MineFragment)
}