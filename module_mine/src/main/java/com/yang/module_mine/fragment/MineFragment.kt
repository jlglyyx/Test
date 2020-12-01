package com.yang.module_mine.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath.MINE_FRAGMENT
import com.yang.module_mine.R


/**
 * @ClassName MineFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
@Route(path = MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fra_mine

    }

    override fun initView() {


    }

    override fun initViewModel() {


    }
}