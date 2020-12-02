package com.yang.module_home.fragment.recommend

import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.view_public_normal_recycler_view.*
import javax.inject.Inject


/**
 * @ClassName RecommendFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 16:04
 */
@Route(path = RoutePath.HOME_RECOMMEND_FRAGMENT)
class RecommendFragment : BaseLazyFragment() {

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory


    override fun getLayout(): Int {

        return R.layout.fra_home_recommend
    }

    override fun initView() {
        initRecyclerView()
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getB()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getB()
            }

        })

    }

    override fun initData() {
        getB()
    }

    override fun initViewModel() {
        DaggerHomeComponent.builder().homeModule(HomeModule(requireActivity())).remoteComponent(
            getRemoteComponent()
        ).build().inject(this)

        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun initRecyclerView() {
        val mutableListOf = mutableListOf<String>()
        for (i in 1..100) {
            mutableListOf.add("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        }
        val videoAdapter = VideoAdapter(R.layout.item_recommend, mutableListOf)
        recyclerView.adapter = videoAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        videoAdapter.setOnItemClickListener { adapter, view, position ->
            val any = adapter.data[position] as String
            ARouter.getInstance().build(RoutePath.HOME_VIDEOPLAY_ACTIVITY).withString("url",any).navigation()
        }

    }

    private fun getB() {
        homeViewModel.getB().observe(this, Observer {
            //Toast.makeText(requireContext(), it,Toast.LENGTH_SHORT).show()
            if (refreshLayout.isRefreshing) {
                refreshLayout.finishRefresh()
            }
            if (refreshLayout.isLoading) {
                refreshLayout.finishLoadMore()
            }
        })
    }


    inner class VideoAdapter(layoutResId: Int, data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

        override fun convert(holder: BaseViewHolder, item: String) {

            val view = holder.getView<ImageView>(R.id.img_video_start)
            Glide.with(view)
                .setDefaultRequestOptions(RequestOptions().frame(4000).centerCrop())
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=993164159,3034267664&fm=26&gp=0.jpg")
                .into(view)

        }
    }


}