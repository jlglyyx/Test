package com.yang.module_picture.ui.fragment

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath.PICTURE_RECOMMEND_FRAGMENT
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_picture.R
import com.yang.module_picture.di.component.DaggerPictureComponent
import com.yang.module_picture.di.module.PictureModule
import com.yang.module_picture.factory.PictureViewModelFactory
import com.yang.module_picture.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.fra_picture_recommend.*
import javax.inject.Inject


/**
 * @ClassName RecommendFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/20 16:54
 */
@Route(path = PICTURE_RECOMMEND_FRAGMENT)
class RecommendFragment : BaseLazyFragment() {

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var list: MutableList<String>
    lateinit var recommendAdapter: RecommendAdapter

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    lateinit var pictureViewModel: PictureViewModel

    override fun getLayout(): Int {

        return R.layout.fra_picture_recommend
    }

    override fun initView() {


    }

    override fun initData() {
        initRecyclerView()
    }


    override fun initViewModel() {

        DaggerPictureComponent.builder().pictureModule(PictureModule(requireActivity()))
            .remoteComponent(
                getRemoteComponent()
            ).build().inject(this)

        pictureViewModel = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)
    }


    private fun initRecyclerView() {
        list = mutableListOf()

        for (i in 0..100) {
            list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3446621678,3819317644&fm=26&gp=0.jpg")
        }
        linearLayoutManager = LinearLayoutManager(requireActivity())
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recommendAdapter = RecommendAdapter(R.layout.item_recommend, list)
        recyclerView.adapter = recommendAdapter
        //recyclerView.layoutManager = linearLayoutManager
        recyclerView.layoutManager = staggeredGridLayoutManager

    }

    inner class RecommendAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(
            layoutResId, data
        ) {
        override fun convert(holder: BaseViewHolder, item: String) {
            val imageView = holder.getView<ImageView>(R.id.img_item)
            Glide.with(imageView).load(R.drawable.img_launcher).into(imageView)
        }

    }
}