package com.yang.module_picture.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath.PICTURE_PICTURE_DESC_ACTIVITY
import com.yang.common_lib.constant.RoutePath.PICTURE_RECOMMEND_FRAGMENT
import com.yang.common_lib.util.dip2px
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_picture.R
import com.yang.module_picture.di.component.DaggerPictureComponent
import com.yang.module_picture.di.module.PictureModule
import com.yang.module_picture.factory.PictureViewModelFactory
import com.yang.module_picture.ui.activity.PictureDescActivity
import com.yang.module_picture.viewmodel.PictureViewModel
import kotlinx.android.synthetic.main.item_recommend.*
import kotlinx.android.synthetic.main.view_public_normal_recycler_view.*
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
class PictureRecommendFragment : BaseLazyFragment() {

    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var list: MutableList<String>
    lateinit var recommendAdapter: RecommendAdapter

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    lateinit var pictureViewModel: PictureViewModel

    var space: Int = 0

    var gridSize = 2


    override fun getLayout(): Int {

        return R.layout.fra_picture_recommend
    }

    override fun initView() {

        space = dip2px(requireContext(), 6f)
    }

    override fun initData() {
        initRecyclerView()
        initTabLayout()
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
            when (i % 5) {
                0 -> list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2912010137,2473581495&fm=26&gp=0.jpg")
                1 -> list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3446621678,3819317644&fm=26&gp=0.jpg")
                2 -> list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20190508%2F17%2F1557306557-mjpEVraUyd.jpg&refer=http%3A%2F%2Fimage.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613786368&t=046ca064c8ad375f19ce2d3bf42d6596")
                3 -> list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.bbra.cn%2FUploadfiles%2Fimgs%2F2013%2F07%2F10%2Ffeng2%2FXbzs_007.jpg&refer=http%3A%2F%2Fwww.bbra.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613786388&t=80bd78001fb33550312557af0903b980")
                4 -> list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3112798566,2640650199&fm=26&gp=0.jpg")
            }

        }
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(gridSize, StaggeredGridLayoutManager.VERTICAL)
        recommendAdapter = RecommendAdapter(R.layout.item_recommend, list)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = recommendAdapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)
                if (position == -1) {
                    return
                }

                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams

                val spanIndex = layoutParams.spanIndex

                if (spanIndex % gridSize == 0) {
                    outRect.left = space
                    outRect.right = space / 2
                } else {
                    outRect.left = space / 2
                    outRect.right = space
                }

            }
        })

        recommendAdapter.setOnItemClickListener { adapter, view, position ->
//            val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(
//               mContext as Activity,
//                siv_item,
//                "ll_transition"
//            )
            ARouter.getInstance()
                .build(PICTURE_PICTURE_DESC_ACTIVITY)
                .navigation()
        }
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

    inner class RecommendAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(
            layoutResId, data
        ) {
        override fun convert(holder: BaseViewHolder, item: String) {
            val imageView = holder.getView<ShapeableImageView>(R.id.siv_item)
            Glide.with(imageView)
                .load(item)
                .error(R.drawable.img_mine_select)
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        imageView.setImageDrawable(resource)
                    }

                })

        }

    }
}