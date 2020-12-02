package com.yang.module_home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.bugly.proguard.s
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.io_main
import com.yang.module_home.repository.HomeRepository
import javax.inject.Inject


/**
 * @ClassName HomeViewModel
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 14:26
 */
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository,private val mContext: Context):BaseViewModel() {

    var fragments = MutableLiveData<MutableList<Fragment>>().apply {
       with(mutableListOf<Fragment>()) {
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           add(ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
           value = this
       }
    }
    var titles = MutableLiveData<MutableList<String>>().apply {
       with(mutableListOf<String>()) {
           add("推荐")
           add("推荐")
           add("推荐")
           add("推荐")
           add("推荐")
           add("推荐")
           add("推荐")
           value = this
       }
    }




    @SuppressLint("CheckResult")
    fun getB():MutableLiveData<String>{
        val s = MutableLiveData<String>()
        homeRepository.getB().compose(io_main(mContext)).subscribe({
            s.value = it
        },{
            s.value = it.message
        })
        return s
    }


}