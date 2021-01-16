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
import com.yang.module_home.ui.fragment.recommend.bean.RecommendTypeBean
import com.yang.module_home.repository.HomeRepository
import io.reactivex.Observable
import okhttp3.RequestBody
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
        for (i in 0..31) {
            var recommendTypeBean: RecommendTypeBean

            when (i % 8) {
                0 -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.TITLE_CODE)
                    recommendTypeBean.type = "$i"
                    recommendTypeBean.id = "$i"
                    mutableListOf.add(recommendTypeBean)

                }
                1 -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.BIG_IMG_CODE)
                    recommendTypeBean.url = "https://vfx.mtime.cn/Video/2019/01/15/mp4/190115161611510728_480.mp4"
                    recommendTypeBean.title = "更新$i 集"
                    recommendTypeBean.desc = "今天抢先看 $i"
                    recommendTypeBean.id = "$i"
                    recommendTypeBean.imgUrl = "http://jlgl.free.idcfengye.com/test/img/$i.jpg"
                    mutableListOf.add(recommendTypeBean)
                }

                else -> {
                    recommendTypeBean = RecommendTypeBean(RecommendTypeBean.SMART_IMG_CODE)
                    when {
                        i%3==0 -> {
                            recommendTypeBean.url = "http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4"
                        }
                        i%3==1 -> {
                            recommendTypeBean.url = "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4"
                        }
                        i%3==2 ->{
                            recommendTypeBean.url = "http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4"
                        }
                        else -> {
                            recommendTypeBean.url = "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4"
                        }
                    }
                    recommendTypeBean.imgUrl = "http://jlgl.free.idcfengye.com/test/img/$i.jpg"
                    recommendTypeBean.title = "更新$i 集"
                    recommendTypeBean.desc = "今天抢先看 $i"
                    recommendTypeBean.id = "$i"
                    mutableListOf.add(recommendTypeBean)
                }
            }

        }

        mutableListOf[0].type = "猜你会追"
        mutableListOf[1].type = "大家都在刷"
        mutableListOf[2].type = "热播电视剧"
        mutableListOf[3].type = "动漫·二次元"
        mutableListOf[4].type = "精彩电影推荐"
        value = mutableListOf



    }

    val refresh = MutableLiveData<Boolean>().also {
        it.value = true
    }

    @SuppressLint("CheckResult")
    fun getRecommendList(): MutableLiveData<MutableList<RecommendTypeBean>> {
        val s = MutableLiveData<MutableList<RecommendTypeBean>>()
        homeRepository.getRecommendList().compose(io_main()).subscribe({
            s.value = it
        }, {
            refresh.value = false
        })
        return s
    }

    @SuppressLint("CheckResult")
    fun uploadFile(files:MutableMap<String, RequestBody>) : String {
        var message = ""
        homeRepository.uploadFile(files).compose(io_main(mContext,"上传中。。。","上传成功")).subscribe({
            message = it.toString()
        },{
            message = it.message.toString()
        })
        return message
    }


}