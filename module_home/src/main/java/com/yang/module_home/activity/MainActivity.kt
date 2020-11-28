package com.yang.module_home.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.module_home.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var fragments:MutableList<Fragment>
    lateinit var titles:MutableList<String>


    override fun getLayout(): Int {

        return R.layout.activity_main
    }

    override fun initView() {
        fragments = mutableListOf()
        titles = mutableListOf()
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        titles.add("1")
        titles.add("2")
        titles.add("3")
        titles.add("4")
        initTabLayout()
        initViewPager()
    }

    override fun initViewModel() {


    }


    private fun initTabLayout(){
        titles.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        tabLayout.setupWithViewPager(viewPager)
    }
    private fun initViewPager(){
        viewPager.adapter = TabAndViewPagerAdapter(supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    }


    inner class TabAndViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentStatePagerAdapter(fm, behavior) {

        override fun getItem(position: Int): Fragment {

            return fragments[position]

        }

        override fun getCount(): Int {

            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }


}
