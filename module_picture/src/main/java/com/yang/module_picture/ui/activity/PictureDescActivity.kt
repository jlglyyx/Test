package com.yang.module_picture.ui.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.down.thread.MultiMoreThreadDownload
import com.yang.common_lib.util.dip2px
import com.yang.common_lib.util.getRemoteComponent
import com.yang.common_lib.util.showShort
import com.yang.module_picture.R
import com.yang.module_picture.di.component.DaggerPictureComponent
import com.yang.module_picture.di.module.PictureModule
import com.yang.module_picture.factory.PictureViewModelFactory
import com.yang.module_picture.viewmodel.PictureViewModel
import com.youth.banner.transformer.ScaleInTransformer
import kotlinx.android.synthetic.main.act_picture_desc.*
import kotlinx.android.synthetic.main.act_picture_desc.recyclerView
import java.io.File
import javax.inject.Inject


/**
 * @ClassName PictureDescActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/21 11:25
 */
@Route(path = RoutePath.PICTURE_PICTURE_DESC_ACTIVITY)
class PictureDescActivity : BaseActivity() {

    @Inject
    lateinit var pictureViewModelFactory: PictureViewModelFactory

    lateinit var pictureViewModel: PictureViewModel

    lateinit var list: MutableList<String>


    override fun getLayout(): Int {
        return R.layout.act_picture_desc
    }

    override fun initView() {
        initViewPager2()
        initRecyclerView()
    }

    override fun initViewModel() {

        DaggerPictureComponent.builder().pictureModule(PictureModule(this))
            .remoteComponent(
                getRemoteComponent()
            ).build().inject(this)

        pictureViewModel = getViewModel(pictureViewModelFactory, PictureViewModel::class.java)

    }


    private fun initViewPager2() {
        list = mutableListOf()

        for (i in 0 until 100) {
            when (i % 5) {
                0 -> list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2912010137,2473581495&fm=26&gp=0.jpg")
                1 -> list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3446621678,3819317644&fm=26&gp=0.jpg")
                2 -> list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20190508%2F17%2F1557306557-mjpEVraUyd.jpg&refer=http%3A%2F%2Fimage.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613786368&t=046ca064c8ad375f19ce2d3bf42d6596")
                3 -> list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.bbra.cn%2FUploadfiles%2Fimgs%2F2013%2F07%2F10%2Ffeng2%2FXbzs_007.jpg&refer=http%3A%2F%2Fwww.bbra.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613786388&t=80bd78001fb33550312557af0903b980")
                4 -> list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3112798566,2640650199&fm=26&gp=0.jpg")
            }

        }


        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(dip2px(this, 0f)))
        compositePageTransformer.addTransformer(ScaleInTransformer())
        viewPager2.setPageTransformer(compositePageTransformer)



        viewPager2.adapter = PictureDescViewPagerAdapter()

        tv_page_index.text = "1/${list.size}"

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tv_page_index.text = "${(position + 1)}/${list.size}"
                recyclerView.smoothScrollToPosition(position)
            }
        })

    }


    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = PictureDescRecyclerViewAdapter(
            R.layout.item_picture_desc_recyclerview,
            list
        ).also {
            it.setOnItemClickListener { adapter, view, position ->
                viewPager2.setCurrentItem(position, false)
            }
        }
        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }


    inner class PictureDescViewPagerAdapter :
        RecyclerView.Adapter<PictureDescViewPagerAdapter.PictureDescViewHolder>() {


        inner class PictureDescViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val imageView: ImageView = itemView.findViewById(R.id.img_item_desc)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureDescViewHolder {

            val inflate = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_picture_desc_viewpager, parent, false)

            return PictureDescViewHolder(inflate)
        }

        override fun getItemCount(): Int {

            return list.size
        }

        override fun onBindViewHolder(holder: PictureDescViewHolder, position: Int) {
            File("${Environment.getExternalStorageDirectory()}/test_picture").also {
                if (!it.exists()){
                    it.mkdirs()
                }
            }
            holder.imageView.setOnLongClickListener {
                MultiMoreThreadDownload(
                    "${Environment.getExternalStorageDirectory()}/test_picture/${System.currentTimeMillis()}.jpg",
                    list[position]
                ).start()

                showShort("开始下载...")
                return@setOnLongClickListener false
            }
            Glide.with(mContext).load(list[position]).into(holder.imageView)
        }
    }


    class PictureDescRecyclerViewAdapter(layoutResId: Int, data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(
            layoutResId, data
        ) {
        override fun convert(holder: BaseViewHolder, item: String) {
            val imageView = holder.getView<ImageView>(R.id.img_item_desc)
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