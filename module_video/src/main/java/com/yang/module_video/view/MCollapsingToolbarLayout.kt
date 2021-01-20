package com.yang.module_video.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.google.android.material.appbar.CollapsingToolbarLayout


/**
 * @ClassName MCollapsingToolbarLayout
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/20 19:34
 */
class MCollapsingToolbarLayout: CollapsingToolbarLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount){
            Log.i("TAG", "onFinishInflate: ${getChildAt(i)}")
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        Log.i("TAG", "dispatchTouchEvent: ")

        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {


        Log.i("TAG", "onInterceptTouchEvent: ")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        Log.i("TAG", "onTouchEvent: ")

        return super.onTouchEvent(event)
    }

}