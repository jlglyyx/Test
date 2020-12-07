package com.yang.module_home.fragment.recommend

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.gson.Gson
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.common_lib.adapter.MBannerAdapter
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.constant.RoutePath.HOME_SEARCH_ACTIVITY
import com.yang.common_lib.data.BannerBean
import com.yang.common_lib.util.dip2px
import com.yang.common_lib.util.getRemoteComponent
import com.yang.common_lib.util.px2dip
import com.yang.common_lib.util.showShort
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.fragment.HomeFragment
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.BIG_IMG_CODE
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.SMART_IMG_CODE
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.TITLE_CODE
import com.yang.module_home.viewmodel.HomeViewModel
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.viewPager
import kotlinx.android.synthetic.main.fra_home.*
import kotlinx.android.synthetic.main.view_public_normal_head_search.*
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

    private lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var gson: Gson

    var space:Int = 0

    lateinit var videoAdapter:VideoAdapter


    override fun getLayout(): Int {

        return R.layout.fra_home_recommend
    }

    override fun initView() {
        space = dip2px(requireContext(),6f)

        ll_search.setOnClickListener {
            ARouter.getInstance().build(HOME_SEARCH_ACTIVITY).navigation()
        }

        initRecyclerView()
        initBanner()
        initSmartRefreshLayout()

    }

    override fun initData() {
        getRecommendList()
    }

    override fun initViewModel() {
        DaggerHomeComponent.builder().homeModule(HomeModule(requireActivity())).remoteComponent(
            getRemoteComponent()
        ).build().inject(this)

        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun initRecyclerView() {

        with(homeViewModel.recommendTypeBeans.value!!){
            videoAdapter = VideoAdapter(mutableListOf())
            Log.i("===TAG===", "initRecyclerView: ${gson.toJson(this)}")
        }

        recyclerView.adapter = videoAdapter
        gridLayoutManager = GridLayoutManager(requireContext(),4)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if (position == 0){//view的下标为0
                    return  4
                }
                val recommendTypeBean = videoAdapter.data[position-1]//view的下标为1 但是data得的为0
                return when(recommendTypeBean.itemType){
                    TITLE_CODE -> 4
                    BIG_IMG_CODE -> 4
                    SMART_IMG_CODE -> 2
                    else -> 4
                }
            }

        }

        recyclerView.layoutManager = gridLayoutManager
        videoAdapter.setOnItemClickListener { adapter, view, position ->
            val any = adapter.data[position] as RecommendTypeBean
            when(any.itemType){
                TITLE_CODE ->{
                    showShort(position)
                }
                else ->{
                    ARouter.getInstance().build(RoutePath.HOME_VIDEOPLAY_ACTIVITY).withString("recommendTypeBean",gson.toJson(any)).navigation()

                }
            }
        }




        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                if (position == -1 || position == 0){
                    return
                }
                val recommendTypeBean = videoAdapter.data[position-1]
                when(recommendTypeBean.itemType){
                    SMART_IMG_CODE ->{
                        if (position%2==0){
                            outRect.left = space/2
                        }else {
                            outRect.right = space/2
                        }
                    }
                }
            }
        })

        activity?.tabLayout.let {
            it?.post {
                val layoutParams = recyclerView.layoutParams as SmartRefreshLayout.LayoutParams
                layoutParams.bottomMargin = it.height+ dip2px(requireContext(),20f)
                recyclerView.layoutParams = layoutParams
            }
        }

    }

    private fun initBanner(){

        val mutableListOf = mutableListOf<BannerBean>()
        for (i in 0..10){
            mutableListOf.add(BannerBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606987532332&di=2bed80227783721facc3aed8ce5c11ac&imgtype=0&src=http%3A%2F%2Fimg.pptjia.com%2Fimage%2F20180711%2F6e2198894a3107f377c490569fa2650c.JPG"))
        }

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.view_banner, null, false)
        val banner = view.findViewById<Banner<*,*>>(R.id.banner)
        banner.addBannerLifecycleObserver(this)
            .setAdapter(MBannerAdapter(mutableListOf)).indicator = CircleIndicator(requireContext())
        videoAdapter.addHeaderView(view)

    }

    private fun getRecommendList() {
        homeViewModel.getRecommendList().observe(this, Observer {
            if (refreshLayout.isRefreshing) {
                refreshLayout.finishRefresh()
                videoAdapter.replaceData(it)
            }else if (refreshLayout.isLoading) {
                refreshLayout.finishLoadMore()
                videoAdapter.addData(it)
            }else{
                videoAdapter.replaceData(it)
            }
        })
    }

    private fun initSmartRefreshLayout(){
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getRecommendList()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getRecommendList()
            }

        })

        homeViewModel.refresh.observe(this, Observer {
            if (!it){
                if (refreshLayout.isRefreshing) {
                    refreshLayout.finishRefresh()
                }
                if (refreshLayout.isLoading) {
                    refreshLayout.finishLoadMore()
                }
            }
        })

    }


    inner class VideoAdapter(data: MutableList<RecommendTypeBean>?) : BaseMultiItemQuickAdapter<RecommendTypeBean, BaseViewHolder>(data) {

        init {
            addItemType(TITLE_CODE,R.layout.item_recommend_title)
            addItemType(BIG_IMG_CODE,R.layout.item_recommend_big_img)
            addItemType(SMART_IMG_CODE,R.layout.item_recommend_smart_img)
        }


        override fun convert(holder: BaseViewHolder, item: RecommendTypeBean) {


            when(item.itemType){
                TITLE_CODE ->{
                    holder.setText(R.id.img_video_type,item.type)
                }
                BIG_IMG_CODE ->{
                    holder.setText(R.id.img_video_desc,item.desc)
                        .setText(R.id.img_video_title,item.title)
                    val view = holder.getView<ImageView>(R.id.img_video_start)
//                    Glide.with(view)
//                        .load(item.imgUrl)
//                        .into(view)
                    Glide.with(view)
                        .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                        .load(item.url)
                        .into(view)
                }
                else ->{
                    holder.setText(R.id.img_video_desc,item.desc)
                        .setText(R.id.img_video_title,item.title)
                    val view = holder.getView<ImageView>(R.id.img_video_start)
                    Glide.with(view)
                        .setDefaultRequestOptions(RequestOptions().frame(1000).centerCrop())
                        .load(item.url)
                        .into(view)
                }
            }



        }
    }


}