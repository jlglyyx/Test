package com.yang.common_lib.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tencent.bugly.proguard.w
import com.yang.common_lib.R


/**
 * @ClassName DiamondFloatingActionButton
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/30 15:44
 */
class DiamondFloatingActionButton : FloatingActionButton {

    companion object {
        private const val TAG = "DiamondFloatingActionBu"
    }


    private var mPaint: Paint = Paint()
    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_launcher)
        .copy(Bitmap.Config.ARGB_8888, true)

    var bitmapResource: Int = 0
        set(value) {
            bitmap =
                BitmapFactory.decodeResource(resources, value).copy(Bitmap.Config.ARGB_8888, true)
        }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        mPaint.color = Color.YELLOW
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.rotate(45f, measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2)
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mPaint)
        val saveLayer =
            canvas.saveLayer(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), null)
        canvas.rotate(-45f, measuredWidth.toFloat() / 2, measuredHeight.toFloat() / 2)
        canvas.translate(
            measuredWidth.toFloat() / 2 - bitmap.width / 2,
            measuredHeight.toFloat() / 2 - bitmap.height / 2
        )
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restoreToCount(saveLayer)
    }
}