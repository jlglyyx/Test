package com.yang.common_lib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView


/**
 * @ClassName LoginVideoView
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/30 17:56
 */
class LoginVideoView:VideoView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(View.getDefaultSize(0,widthMeasureSpec), View.getDefaultSize(0,heightMeasureSpec))
    }
}