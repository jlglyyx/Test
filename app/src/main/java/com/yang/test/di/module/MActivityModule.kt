package com.yang.test.di.module

import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.scope.ActivityScope
import com.yang.test.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MActivityModule {
    @ActivityScope
    @Provides
    fun provideBaseActivity(): BaseActivity {
        return MainActivity()
    }
}