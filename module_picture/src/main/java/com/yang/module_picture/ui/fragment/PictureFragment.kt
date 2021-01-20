package com.yang.module_picture.ui.fragment

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
import com.yang.module_picture.R
import kotlinx.android.synthetic.main.fra_picture.*
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
@Route(path = RoutePath.PICTURE_FRAGMENT)
class PictureFragment : BaseFragment() {

    private lateinit var fragments: MutableList<Fragment>
    private lateinit var titles: MutableList<String>


    override fun getLayout(): Int {
        return R.layout.fra_picture
    }

    override fun initView() {
        fragments = mutableListOf()
        titles = mutableListOf()
        with(fragments){
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
            add(ARouter.getInstance().build(RoutePath.PICTURE_RECOMMEND_FRAGMENT).navigation() as Fragment)
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



    private fun initTabLayout() {

        var isOpenFirst = true
        var isCloseFirst = true

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                verticalOffset == 0 -> {
                    if (isOpenFirst){
                        //ll_search.visibility = View.VISIBLE
                        isOpenFirst = false
                        isCloseFirst = true
                    }


                }
                abs(verticalOffset) >=appBarLayout.totalScrollRange -> {
                    if (isCloseFirst){
                        //ll_search.visibility = View.INVISIBLE
                        isCloseFirst = false
                        isOpenFirst = true
                    }


                }
                else -> {



                }
            }
        })


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