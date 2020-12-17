package com.yang.module_mine.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath.MINE_FRAGMENT
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.fra_mine.*
import java.lang.Math.abs


/**
 * @ClassName MineFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
@Route(path = MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    var list = mutableListOf<String>()

    override fun getLayout(): Int {
        return R.layout.fra_mine

    }

    override fun initView() {
        for (i in 0..100){
            list.add("$i")
        }

        recyclerView.layoutManager = object : LinearLayoutManager(requireContext()){
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        recyclerView.adapter = MineFragmentAdapter(R.layout.item_menu,list)
        val value = object : CustomTarget<Drawable>(){
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                ll_a.background = resource
            }


        }

        //Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607509937585&di=97b9e651110aea14947abc0a9fcf1bc8&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F07448b820b27cd7394c0836161e8e76f95d016da27e8a-LtXDaF_fw658").into(value)
        //Glide.with(this).load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2400147585,2493813740&fm=26&gp=0.jpg0").into(siv_head)






//        var isOpenFirst = true
//        var isCloseFirst = true
//
//        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            when {
//                verticalOffset == 0 -> {
//                    if (isOpenFirst){
//                        rl_toolbar.visibility = View.VISIBLE
//                        isOpenFirst = false
//                        isCloseFirst = true
//                    }
//
//
//                }
//                kotlin.math.abs(verticalOffset) >=appBarLayout.totalScrollRange -> {
//                    if (isCloseFirst){
//                        rl_toolbar.visibility = View.INVISIBLE
//                        isCloseFirst = false
//                        isOpenFirst = true
//                    }
//
//
//                }
//                else -> {
//
//
//
//                }
//            }
//            Log.i("ssssssssssss", "onOffsetChanged: $verticalOffset  ====${appBarLayout.totalScrollRange}")
//        })








    }

    override fun initViewModel() {


    }


    inner class MineFragmentAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


        override fun convert(holder: BaseViewHolder, item: String) {


        }


    }

}