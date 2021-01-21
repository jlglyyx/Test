package com.yang.module_home.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import com.yang.common_lib.adapter.TabAndViewPagerAdapter
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.HOME_RECOMMEND_FRAGMENT
import com.yang.module_home.R
import kotlinx.android.synthetic.main.fra_home.*
import kotlinx.android.synthetic.main.view_public_normal_head_search.*
import kotlin.math.abs


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
class HomeFragment : BaseFragment() {

    private lateinit var fragments: MutableList<Fragment>
    private lateinit var titles: MutableList<String>


    override fun getLayout(): Int {
        return R.layout.fra_home
    }

    override fun initView() {
        fragments = mutableListOf()
        titles = mutableListOf()
        with(fragments){
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(HOME_RECOMMEND_FRAGMENT).navigation() as Fragment)
        }
        with(titles){
            add("推荐")
            add("推荐")
            add("推荐")
            add("推荐")
            add("推荐")
            add("推荐")
            add("推荐")
        }
        initViewPager()
    }


    override fun initViewModel() {

    }

    override fun setStatusPadding(): Boolean {
        return false
    }



    @SuppressLint("ClickableViewAccessibility")
    private fun initTabLayout() {
        toolbar.setOnTouchListener { v, event ->
            return@setOnTouchListener ll_seek.dispatchTouchEvent(event)
        }

        ll_search.setOnClickListener {

        }
        img_add.setOnClickListener {

        }

    }

    private fun initViewPager() {
        viewPager.adapter = TabAndViewPagerAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragments,
            titles
        )
        viewPager.offscreenPageLimit = fragments.size - 1
        tabLayout.setupWithViewPager(viewPager)
        initTabLayout()
    }
}