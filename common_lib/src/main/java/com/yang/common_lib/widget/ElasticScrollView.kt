package com.yang.common_lib.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.tencent.bugly.proguard.t


/**
 * @ClassName ElasticScrollView
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/6 10:26
 */
class ElasticScrollView : ScrollView {

    companion object {
        private const val TAG = "ElasticScrollView"
        const val TAG_SCROLL = "scroll"
    }


    var contentView: View? = null
    var rect: Rect = Rect()

    var startY = 0.0f
    var translateAnimation: TranslateAnimation? = null

    var canChildViewScroll = false
    var canViewScroll = true
    var reset = true


    var view: View? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            contentView = getChildAt(0)
        }

        val linearLayout = contentView as LinearLayout

        for (i in 0 until linearLayout.childCount) {

            val childAt = linearLayout.getChildAt(i)

            if (childAt.tag == TAG_SCROLL) {
                view = childAt
                Log.i(TAG, "dispatchTouchEvent: $childAt  ${view}")
            }

        }


        (view as RecyclerView).addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                canChildViewScroll = if (!recyclerView.canScrollVertically(1)) {
                    Log.i(TAG, "onScrolled: 底部")
                    true

                } else if (!recyclerView.canScrollVertically(-1)) {
                    Log.i(TAG, "onScrolled: 顶部")
                    true
                } else {
                    //Log.i(TAG, "onScrolled: $dy")
                    false
                }


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i(TAG, "onScrollStateChanged: $newState")
            }
        })
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        contentView.run {
            rect.set(left, top, right, bottom)
            Log.i(TAG, "onLayout: $left $top $right $bottom")
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {


        Log.i(TAG, "dispatchTouchEvent: $canChildViewScroll   $canViewScroll")
        if (!canChildViewScroll && !canViewScroll) {
            return super.dispatchTouchEvent(ev)
        }

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startY = ev.y
                Log.i(TAG, "dispatchTouchEvent: $startY")
            }
            MotionEvent.ACTION_MOVE -> {

                val offset = ((ev.y - startY) * 0.5f).toInt()

                Log.i(TAG, "dispatchTouchEvent: $offset")
                rect.run {
                    contentView?.layout(
                        left, top + offset, right, bottom + offset
                    )
                }
                contentView?.let {
                    Log.i(TAG, "dispatchTouchEvent=s: ${contentView?.top}   ${rect.top}")
                    // 开启动画
                    translateAnimation = TranslateAnimation(
                        0f, 0f, it.top.toFloat(),
                        rect.top.toFloat()
                    )
                    translateAnimation?.duration = 500
                }

            }
            MotionEvent.ACTION_UP -> {

                translateAnimation?.let {
                    it.cancel()
                    contentView?.startAnimation(it)
                }
                rect.run {
                    contentView?.layout(
                        left, top, right, bottom
                    )
                }

                //isCanMoved = false
            }


        }


        return super.dispatchTouchEvent(ev)
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        Log.i(TAG, "onScrollChanged: ${contentView?.bottom}")
        Log.i(TAG, "onScrollChanged: $l $t $oldl $oldt")
        Log.i(TAG, "isScrollTop: ${contentView?.measuredHeight!!}  $height  $scrollY")
        when {
            contentView?.measuredHeight!! <= height + scrollY -> {
                canViewScroll = true
                Log.i(TAG, "onScrollChanged: 底部")
            }
            scrollY == 0 -> {
                canViewScroll = true
                Log.i(TAG, "onScrollChanged: 顶部")
            }
            else -> {
                canViewScroll = false


            }
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

}