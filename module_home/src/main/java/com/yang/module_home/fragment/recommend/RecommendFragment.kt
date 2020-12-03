package com.yang.module_home.fragment.recommend

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
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
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.dip2px
import com.yang.common_lib.util.getRemoteComponent
import com.yang.common_lib.util.getScreenPx
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.BANNER_CODE
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.BIG_IMG_CODE
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.SMART_IMG_CODE
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean.Companion.TITLE_CODE
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

    lateinit var gridLayoutManager: GridLayoutManager

    var space:Int = 0


    override fun getLayout(): Int {

        return R.layout.fra_home_recommend
    }

    override fun initView() {
        initRecyclerView()
        space = dip2px(requireContext(),6f)
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


        val videoAdapter = VideoAdapter(homeViewModel.recommendTypeBeans.value!!)
        recyclerView.adapter = videoAdapter
        gridLayoutManager = GridLayoutManager(requireContext(),4)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val recommendTypeBean = videoAdapter.data[position]
                return when(recommendTypeBean.itemType){
                    BANNER_CODE -> 4
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
            ARouter.getInstance().build(RoutePath.HOME_VIDEOPLAY_ACTIVITY).withString("url",any.url).navigation()
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
                if (position == -1){
                    return
                }
                val recommendTypeBean = videoAdapter.data[position]
                when(recommendTypeBean.itemType){
                    SMART_IMG_CODE ->{
                        if (position%2==0){
                            outRect.right = space/2
                        }else {
                            outRect.left = space/2
                        }
                    }
                }
            }
        })

    }

    private fun getB() {
        homeViewModel.getB().observe(this, Observer {
            if (refreshLayout.isRefreshing) {
                refreshLayout.finishRefresh()
            }
            if (refreshLayout.isLoading) {
                refreshLayout.finishLoadMore()
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
                TITLE_CODE ->{}
                else ->{
                    val view = holder.getView<ImageView>(R.id.img_video_start)
                    Glide.with(view)
                        .setDefaultRequestOptions(RequestOptions().frame(4000).centerCrop())
                        .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=993164159,3034267664&fm=26&gp=0.jpg")
                        .into(view)

                }
            }



        }
    }


}