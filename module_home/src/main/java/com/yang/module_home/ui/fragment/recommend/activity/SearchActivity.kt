package com.yang.module_home.ui.fragment.recommend.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.showShort
import com.yang.module_home.R


/**
 * @ClassName SearchActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 16:05
 */

@Route(path = RoutePath.HOME_SEARCH_ACTIVITY)
class SearchActivity: BaseActivity() {


    override fun getLayout(): Int {

        return R.layout.act_search

    }

    override fun initView() {


        showShort("搜索")
    }

    override fun initViewModel() {


    }
}