package com.yang.common_lib.remote.di.module

import com.yang.common_lib.scope.RemoteScope
import dagger.Module
import dagger.Provides

@Module
class RemoteModule {

    @RemoteScope
    @Provides
    fun provideString(): String {
        return "BaseApplication.baseApplication"
    }
//    @RemoteScope
//    @Provides
//    fun provideApplicationContext(baseApplication:BaseApplication): Context {
//        return baseApplication.applicationContext
//    }

}