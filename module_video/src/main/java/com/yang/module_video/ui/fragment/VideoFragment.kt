package com.yang.module_video.ui.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.appbar.AppBarLayout
import com.yang.common_lib.adapter.TabAndViewPagerAdapter
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.VIDEO_SEARCH_ACTIVITY
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_video.R
import com.yang.module_video.di.component.DaggerVideoComponent
import com.yang.module_video.di.module.VideoModule
import com.yang.module_video.factory.VideoViewModelFactory
import com.yang.module_video.ui.fragment.recommend.activity.UploadFileActivity
import com.yang.module_video.viewmodel.VideoViewModel
import kotlinx.android.synthetic.main.fra_video.*
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
@Route(path = RoutePath.VIDEO_FRAGMENT)
class VideoFragment : BaseFragment() {

    private lateinit var fragments: MutableList<Fragment>
    private lateinit var titles: MutableList<String>
    private lateinit var videoViewModel: VideoViewModel

    @Inject
    lateinit var videoViewModelFactory: VideoViewModelFactory

    override fun getLayout(): Int {
        return R.layout.fra_video
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        fragments = videoViewModel.fragments.value!!
        titles = videoViewModel.titles.value!!
        initViewPager()

        toolbar.setOnTouchListener { v, event ->
            return@setOnTouchListener ll_seek.dispatchTouchEvent(event)
        }

        ll_search.setOnClickListener {
            ARouter.getInstance().build(VIDEO_SEARCH_ACTIVITY).navigation()
        }
        img_add.setOnClickListener {

            startActivity(Intent(requireContext(), UploadFileActivity::class.java))
        }


    }


    override fun initViewModel() {

        DaggerVideoComponent.builder().videoModule(VideoModule(requireActivity()))
            .remoteComponent(getRemoteComponent()).build().inject(this)

        videoViewModel = ViewModelProvider(this, videoViewModelFactory).get(VideoViewModel::class.java)
    }

    override fun setStatusPadding(): Boolean {
        return false
    }


    private fun initTabLayout() {




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