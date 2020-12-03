package com.yang.common_lib.adapter

import android.media.Image
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.yang.common_lib.data.BannerBean
import com.youth.banner.adapter.BannerAdapter


/**
 * @ClassName BannerAdapter
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 14:19
 */
class MBannerAdapter(mData: MutableList<BannerBean>) : BannerAdapter<BannerBean, MBannerAdapter.BannerViewHolder>(mData) {


    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var shapeableImageView:ShapeableImageView = itemView as ShapeableImageView


    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {

        val shapeableImageView = ShapeableImageView(parent.context)
        shapeableImageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        shapeableImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(shapeableImageView)

    }

    override fun onBindView(holder: BannerViewHolder, data: BannerBean, position: Int, size: Int) {
        Glide.with(holder.shapeableImageView).load(data.url).into(holder.shapeableImageView)
    }


}