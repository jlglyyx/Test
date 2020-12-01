package com.yang.common_lib.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


/**
 * @ClassName TabAndViewPagerAdapter
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 14:52
 */
class TabAndViewPagerAdapter(fm: FragmentManager, behavior: Int,private val fragments:MutableList<Fragment>,private val titles:MutableList<String>) : FragmentStatePagerAdapter(fm, behavior) {

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