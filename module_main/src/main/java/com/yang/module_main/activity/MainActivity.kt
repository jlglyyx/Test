package com.yang.module_main.activity

import android.app.Activity
import android.util.DisplayMetrics
import androidx.core.view.GravityCompat
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.yang.common_lib.adapter.TabAndViewPagerAdapter
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.MAIN_ACTIVITY
import com.yang.common_lib.util.getScreenPx
import com.yang.common_lib.util.showShort
import com.yang.module_main.R
import com.yang.module_main.fragment.LeftMenuFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field

@Route(path = MAIN_ACTIVITY)
class MainActivity : BaseActivity() {

    lateinit var fragments: MutableList<Fragment>
    lateinit var titles: MutableList<String>
    lateinit var imgSelect: MutableList<Int>
    lateinit var imgUnSelect: MutableList<Int>
    private var l: Long = 0

    override fun getLayout(): Int {

        return R.layout.activity_main
    }

    override fun initView() {
        fragments = mutableListOf()
        titles = mutableListOf()
        imgSelect = mutableListOf()
        imgUnSelect = mutableListOf()
        fragments.add(ARouter.getInstance().build(RoutePath.HOME_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        fragments.add(ARouter.getInstance().build(RoutePath.MINE_FRAGMENT).navigation() as Fragment)
        titles.add("首页")
        titles.add("视频")
        titles.add("更多")
        titles.add("我的")

        imgSelect.add(R.drawable.img_home_select)
        imgSelect.add(R.drawable.img_mine_select)
        imgSelect.add(R.drawable.img_home_select)
        imgSelect.add(R.drawable.img_mine_select)

        imgUnSelect.add(R.drawable.img_home_unselect)
        imgUnSelect.add(R.drawable.img_mine_unselect)
        imgUnSelect.add(R.drawable.img_home_unselect)
        imgUnSelect.add(R.drawable.img_mine_unselect)

        initViewPager()
        initDrawerLayout()

    }

    override fun initViewModel() {


    }


    private fun initTabLayout() {

        for (i in imgSelect.indices) {
            if (i == 0) {
                tabLayout.getTabAt(i)?.setIcon(imgSelect[i])
            } else {
                tabLayout.getTabAt(i)?.setIcon(imgUnSelect[i])
            }

        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.setIcon(imgUnSelect[tab.position])
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.setIcon(imgSelect[tab.position])
            }

        })


    }

    private fun initViewPager() {
        viewPager.canSlide = false
        viewPager.adapter = TabAndViewPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, titles
        )
        viewPager.offscreenPageLimit = fragments.size - 1

        tabLayout.setupWithViewPager(viewPager)



        initTabLayout()
    }

    private fun initDrawerLayout() {
        val supportFragmentManager = supportFragmentManager
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fl_left, LeftMenuFragment())
        beginTransaction.commit()
        val layoutParams = fl_left.layoutParams as DrawerLayout.LayoutParams
        fl_left.post {
            layoutParams.rightMargin = fl_left.width - getScreenPx(this)[0]
            fl_left.layoutParams = layoutParams
        }
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
            return
        }
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - l > 2000) {
            showShort("再按一次退出应用")
            l = currentTimeMillis
        } else {
            finish()
        }


    }


}

