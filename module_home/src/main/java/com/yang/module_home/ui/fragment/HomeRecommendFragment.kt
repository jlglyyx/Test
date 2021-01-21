package com.yang.module_home.ui.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.tabs.TabLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath.HOME_RECOMMEND_FRAGMENT
import com.yang.common_lib.util.dip2px
import com.yang.module_home.R
import com.yang.module_home.data.HomeRecommendTypeBean
import com.yang.module_home.data.HomeRecommendTypeBean.Companion.TYPE_PICTURE_CODE
import com.yang.module_home.data.HomeRecommendTypeBean.Companion.TYPE_VIDEO_CODE
import kotlinx.android.synthetic.main.view_public_normal_recycler_view.*


/**
 * @ClassName RecommendFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/20 16:54
 */
@Route(path = HOME_RECOMMEND_FRAGMENT)
class HomeRecommendFragment:BaseLazyFragment() {

    private lateinit var list: MutableList<HomeRecommendTypeBean>

    override fun getLayout(): Int {

        return R.layout.fra_home_recommend
    }

    override fun initView() {
        initTabLayout()
        initRecyclerView()
    }

    override fun initData() {

    }

    override fun initViewModel() {

    }

    private fun initTabLayout(){

        activity?.findViewById<TabLayout>(R.id.mainTabLayout)?.let {
            it.post {
                val layoutParams = recyclerView.layoutParams as SmartRefreshLayout.LayoutParams
                layoutParams.bottomMargin = it.height + dip2px(requireContext(), 20f)
                recyclerView.layoutParams = layoutParams
            }
        }
    }

    private fun initRecyclerView(){
        list = mutableListOf()
        for (i in 0 until 100){
            list.add(HomeRecommendTypeBean(TYPE_PICTURE_CODE))
        }
        recyclerView.adapter = HomeRecommendAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


    inner class HomeRecommendAdapter(data: MutableList<HomeRecommendTypeBean>?) :
        BaseMultiItemQuickAdapter<HomeRecommendTypeBean, BaseViewHolder>(data) {

        init {
            addItemType(TYPE_PICTURE_CODE, R.layout.item_recommend_type_picture)
            addItemType(TYPE_VIDEO_CODE, R.layout.item_recommend_type_picture)
        }


        override fun convert(holder: BaseViewHolder, item: HomeRecommendTypeBean) {


            when (item.itemType) {
                TYPE_PICTURE_CODE -> {

                }
                TYPE_VIDEO_CODE -> {

                }
                else -> {

                }
            }


        }
    }
}