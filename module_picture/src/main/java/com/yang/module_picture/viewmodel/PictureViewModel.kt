package com.yang.module_picture.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.constant.RoutePath
import com.yang.module_picture.repository.PictureRepository
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
class PictureViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
    private val mContext: Context
) : BaseViewModel() {

    var fragments = MutableLiveData<MutableList<Fragment>>().apply {
        with(mutableListOf<Fragment>()) {
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
                    .navigation() as Fragment
            )
            add(
                ARouter.getInstance().build(RoutePath.VIDEO_RECOMMEND_FRAGMENT)
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



}