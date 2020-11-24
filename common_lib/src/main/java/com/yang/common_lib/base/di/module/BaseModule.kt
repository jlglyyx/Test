package com.yang.common_lib.base.di.module

import android.content.Context
import com.yang.common_lib.base.app.BaseApplication
import com.yang.common_lib.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class BaseModule {

    @ApplicationScope
    @Provides
    fun provideBaseApplication(): BaseApplication {
        return BaseApplication.baseApplication
    }
    @ApplicationScope
    @Provides
    fun provideApplicationContext(): Context {
        return BaseApplication.applicationContext
    }

}