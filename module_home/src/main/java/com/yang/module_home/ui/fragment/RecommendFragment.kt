package com.yang.module_home.ui.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath.HOME_RECOMMEND_FRAGMENT
import com.yang.module_home.R


/**
 * @ClassName RecommendFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/20 16:54
 */
@Route(path = HOME_RECOMMEND_FRAGMENT)
class RecommendFragment:BaseLazyFragment() {

    override fun getLayout(): Int {

        return R.layout.fra_recommend
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initViewModel() {

    }
}