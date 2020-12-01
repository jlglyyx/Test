package com.yang.module_home.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.module_home.R


/**
 * @ClassName HomeFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 10:37
 */
@Route(path = RoutePath.HOME_FRAGMENT)
class HomeFragment: BaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fra_home
    }

    override fun initView() {
    }

    override fun initViewModel() {
    }
}