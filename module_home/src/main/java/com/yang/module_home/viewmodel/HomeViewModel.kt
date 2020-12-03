package com.yang.module_home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.tencent.bugly.proguard.s
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.io_main
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean
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
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val mContext: Context
) : BaseViewModel() {

    var fragments = MutableLiveData<MutableList<Fragment>>().apply {
        with(mutableListOf<Fragment>()) {
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.HOME_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            value = this
        }
    }
    var titles = MutableLiveData<MutableList<String>>().apply {
        with(mutableListOf<String>()) {
            add("推荐")
            add("爱看")
            add("电视剧")
            add("电影")
            add("综艺")
            add("少儿")
            add("动漫")
            value = this
        }
    }
    var recommendTypeBeans = MutableLiveData<MutableList<RecommendTypeBean>>().apply {
        val mutableListOf = mutableListOf<RecommendTypeBean>()
        for (i in 0..30) {
            var recommendTypeBean: RecommendTypeBean
            Log.i("TAG", ": $i  ${i % 8}")

            when (i % 8) {
                0 -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.TITLE_CODE)
                    mutableListOf.add(recommendTypeBean)

                }
                1 -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.BIG_IMG_CODE)
                    recommendTypeBean.url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                    mutableListOf.add(recommendTypeBean)
                }

                else -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.SMART_IMG_CODE)
                    recommendTypeBean.url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                    mutableListOf.add(recommendTypeBean)
                }
            }

        }
        value = mutableListOf


        Log.i("ssssssssssss", ": ${Gson().toJson(mutableListOf)}")

    }


    @SuppressLint("CheckResult")
    fun getB(): MutableLiveData<String> {
        val s = MutableLiveData<String>()
        homeRepository.getB().compose(io_main(mContext)).subscribe({
            s.value = it
        }, {
            s.value = it.message
        })
        return s
    }


}