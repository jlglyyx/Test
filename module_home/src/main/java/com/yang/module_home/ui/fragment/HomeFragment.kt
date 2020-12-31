package com.yang.module_home.ui.fragment

import android.content.Intent
import android.util.Log
import android.view.View.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import com.yang.common_lib.adapter.TabAndViewPagerAdapter
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.HOME_SEARCH_ACTIVITY
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.ui.fragment.recommend.activity.UploadFileActivity
import com.yang.module_home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.fra_home.*
import kotlinx.android.synthetic.main.view_public_normal_head_search.*
import javax.inject.Inject
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
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun getLayout(): Int {
        return R.layout.fra_home
    }

    override fun initView() {
        fragments = homeViewModel.fragments.value!!
        titles = homeViewModel.titles.value!!
        initViewPager()
        ll_search.setOnClickListener {
            ARouter.getInstance().build(HOME_SEARCH_ACTIVITY).navigation()
        }
        img_add.setOnClickListener {

            startActivity(Intent(requireContext(),UploadFileActivity::class.java))
        }
    }


    override fun initViewModel() {

        DaggerHomeComponent.builder().homeModule(HomeModule(requireActivity()))
            .remoteComponent(getRemoteComponent()).build().inject(this)

        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
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
                        ll_search.visibility = VISIBLE
                        isOpenFirst = false
                        isCloseFirst = true
                    }


                }
                abs(verticalOffset)>=appBarLayout.totalScrollRange -> {
                    if (isCloseFirst){
                        ll_search.visibility = INVISIBLE
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

//        activity?.tabLayout.let {
//            it?.post {
////                val layoutParams = viewPager.layoutParams as LinearLayout.LayoutParams
////                layoutParams.bottomMargin = it.height+100
////                viewPager.layoutParams = layoutParams
//                viewPager.setPadding(0,0,0,it.height+80)
//            }
//        }
    }


}