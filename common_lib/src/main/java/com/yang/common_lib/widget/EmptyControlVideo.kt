package com.yang.common_lib.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yang.common_lib.R
import kotlinx.android.synthetic.main.view_empty_control_video.view.*


/**
 * @ClassName EmptyControlVideo
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/5 11:41
 */
class EmptyControlVideo : StandardGSYVideoPlayer {

    companion object{
        private const val TAG = "EmptyControlVideo"
    }

    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.view_empty_control_video
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        
        for (i in 0 until surface_container.childCount){
            Log.i(TAG, "onFinishInflate: ${surface_container.getChildAt(i)}  ${surface_container.getChildAt(i).height} ${surface_container.getChildAt(i).paddingTop}  ${surface_container.getChildAt(i).paddingBottom}")
            surface_container.getChildAt(i).setPadding(0,0,0,0)
        }
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun touchDoubleUp() {
        //super.touchDoubleUp();
        //不需要双击暂停
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        Log.i(TAG, "onMeasure: $widthSize  $heightSize  $widthMode  $heightMode")

       // setMeasuredDimension(1000,1000)
    }



}