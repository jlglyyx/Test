package com.yang.common_lib.widget

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView


/**
 * @ClassName DampingScrollView
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/14 17:08
 */
class DampingScrollView : ScrollView {


    companion object {
        private const val TAG = "DampingScrollView"
    }

    //移动因子, 是一个百分比, 比如手指移动了100px, 那么View就只移动50px
    //目的是达到一个延迟的效果
    private var MOVE_FACTOR = 0.5f

    //松开手指后, 界面回到正常位置需要的动画时间
    private var ANIM_TIME = 300

    //ScrollView的子View， 也是ScrollView的唯一一个子View
    private var contentView: View? = null

    //手指按下时的Y值, 用于在移动时计算移动距离
    //如果按下时不能上拉和下拉， 会在手指移动时更新为当前手指的Y值
    private var startY = 0f

    //用于记录正常的布局位置
    private var originalRect: Rect = Rect()

    //手指按下时记录是否可以继续下拉
    private var canPullDown = false

    //手指按下时记录是否可以继续上拉
    private var canPullUp = false

    //在手指滑动的过程中记录是否移动了布局
    private var isMoved = false


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    @SuppressLint("ObjectAnimatorBinding")
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (contentView == null) {
            return super.dispatchTouchEvent(ev)
        }


        if (ev.y >= height || ev.y <= 0){
            if (isMoved){
                onUpEvent(ev)
                return true
            }

        }

        when (ev.action) {

            MotionEvent.ACTION_DOWN -> {
                canPullDown = isCanPullDown()
                canPullUp = isCanPullUp()
                startY = ev.y

            }
            MotionEvent.ACTION_UP -> {

                if (isMoved) {

                    val ofFloat = ObjectAnimator.ofFloat(contentView, "translationX", 0f, 1f)
                    ofFloat.duration = 1000
                    ofFloat.start()
                    with(originalRect) {
                        contentView?.layout(left, top, right, bottom)
                        Log.i(TAG, "onLayout====: $left,$top,$right,$bottom")
                    }


                    canPullDown = false
                    canPullUp = false
                    isMoved = false
                }

            }
            MotionEvent.ACTION_MOVE -> {
                onUpEvent(ev)
            }
        }
        return super.dispatchTouchEvent(ev)

    }



    private fun onUpEvent(ev: MotionEvent){
        if (!canPullDown || !canPullUp) {
            canPullDown = isCanPullDown()
            canPullUp = isCanPullUp()
            startY = ev.y
            return
        }

        //计算手指移动的距离

        //计算手指移动的距离
        val nowY = ev.y
        val deltaY = (nowY - startY).toInt()

        //是否应该移动布局

        //是否应该移动布局
        val shouldMove = (canPullDown && deltaY > 0 //可以下拉， 并且手指向下移动
                || canPullUp && deltaY < 0 //可以上拉， 并且手指向上移动
                || canPullUp && canPullDown) //既可以上拉也可以下拉（这种情况出现在ScrollView包裹的控件比ScrollView还小）


        if (shouldMove) {
            //计算偏移量
            val offset = (deltaY * MOVE_FACTOR).toInt()

            //随着手指的移动而移动布局
            contentView!!.layout(
                originalRect.left, originalRect.top + offset,
                originalRect.right, originalRect.bottom + offset
            )
            isMoved = true //记录移动了布局
        }
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            contentView = getChildAt(0)
        }

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        if (contentView == null) {
            return
        }
        originalRect.set(contentView!!.left, contentView!!.top, contentView!!.right, contentView!!.bottom)
        Log.i(TAG, "onLayout: ${contentView!!.left},${contentView!!.top},${contentView!!.right},${contentView!!.bottom}")

        Log.i(TAG, "===onLayout===: $l,$t,$r,$b")


    }


    //顶部
    private fun isCanPullDown(): Boolean {
        return scrollY == 0 || contentView!!.height < height + scrollY
    }

    //底部
    private fun isCanPullUp(): Boolean {


        return contentView!!.height <= height + scrollY
    }
}