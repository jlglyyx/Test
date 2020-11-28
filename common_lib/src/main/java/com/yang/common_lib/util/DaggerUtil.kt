@file:JvmName("DaggerUtil")

package com.yang.common_lib.util

import com.yang.common_lib.base.di.component.BaseComponent
import com.yang.common_lib.base.di.component.DaggerBaseComponent
import com.yang.common_lib.base.di.module.BaseModule
import com.yang.common_lib.remote.di.component.DaggerRemoteComponent
import com.yang.common_lib.remote.di.component.RemoteComponent

private const val TAG = "DaggerUtil"

fun getBaseComponent(): BaseComponent {

    return DaggerBaseComponent
        .builder()
        .baseModule(BaseModule())
        .build()!!
}

fun getRemoteComponent(): RemoteComponent {

    return DaggerRemoteComponent.builder()
        .baseComponent(
            DaggerBaseComponent
                .builder()
                .baseModule(BaseModule())
                .build()
        )
        .build()!!
}



