package com.yang.common_lib.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.yang.common_lib.R
import com.yang.common_lib.util.getStatusBarHeight


/**
 * @ClassName MToolbarView
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/2 11:02
 */
class MToolbarView :RelativeLayout {

    private lateinit var mContext: Context

    private lateinit var leftImg: ImageView
    private lateinit var rightImg: ImageView
    private lateinit var leftTv: TextView
    private lateinit var centerTv: TextView
    private lateinit var rightTv: TextView
    private var leftImgSrc = 0
    private var rightImgSrc = 0
    private lateinit var leftTvContent: String
    private lateinit var centerTvContent: String
    private lateinit var rightTvContent: String
    private lateinit var rl_toolbar:RelativeLayout

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        this.mContext = context!!
        init(mContext,attrs)
    }

    interface OnItemClick {
        fun onLeftClickListener()
        fun onRightClickListener()
    }

    private var onItemClick: OnItemClick? = null


    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_toolbar, this)
        leftImg = view.findViewById(R.id.img_back)
        rightImg = view.findViewById(R.id.img_into)
        leftTv = view.findViewById(R.id.tv_content_left)
        centerTv = view.findViewById(R.id.tv_content_center)
        rightTv = view.findViewById(R.id.tv_content_right)
        rl_toolbar = view.findViewById(R.id.rl_toolbar)

        val layoutParams = rl_toolbar.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight(context)
        rl_toolbar.layoutParams = layoutParams

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadToolbarView)
        leftTv.text = typedArray.getString(R.styleable.HeadToolbarView_leftContent)
        rightTv.text = typedArray.getString(R.styleable.HeadToolbarView_rightContent)
        centerTv.text = typedArray.getString(R.styleable.HeadToolbarView_centerContent)
        leftImg.setImageResource(
            typedArray.getResourceId(
                R.styleable.HeadToolbarView_leftImg,
                R.drawable.img_back
            )
        )
        rightImg.setImageResource(
            typedArray.getResourceId(
                R.styleable.HeadToolbarView_rightImg,
                R.drawable.img_forword
            )
        )
        val aBoolean =
            typedArray.getBoolean(R.styleable.HeadToolbarView_isLeftContent, true)
        val aBoolean1 =
            typedArray.getBoolean(R.styleable.HeadToolbarView_isCenterContent, false)
        val aBoolean2 =
            typedArray.getBoolean(R.styleable.HeadToolbarView_isRighrContent, true)
        val aBoolean3 =
            typedArray.getBoolean(R.styleable.HeadToolbarView_isLeftImg, false)
        val aBoolean4 =
            typedArray.getBoolean(R.styleable.HeadToolbarView_isRightImg, true)
        if (aBoolean) {
            leftTv.visibility = View.GONE
        } else {
            leftTv.visibility = View.VISIBLE
        }
        if (aBoolean1) {
            centerTv.visibility = View.GONE
        } else {
            centerTv.visibility = View.VISIBLE
        }
        if (aBoolean2) {
            rightTv.visibility = View.GONE
        } else {
            rightTv.visibility = View.VISIBLE
        }
        if (aBoolean3) {
            leftImg.visibility = View.GONE
        } else {
            leftImg.visibility = View.VISIBLE
        }
        if (aBoolean4) {
            rightImg.visibility = View.GONE
        } else {
            rightImg.visibility = View.VISIBLE
        }
        typedArray.recycle()
        leftImg.setOnClickListener { (context as Activity).finish() }
    }

    fun initCenterTitle(string: String?) {
        centerTv.text = string
    }

    fun getOnItemClick(): OnItemClick? {
        return onItemClick
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
        leftTv.setOnClickListener { onItemClick.onLeftClickListener() }
        rightTv.setOnClickListener { onItemClick.onRightClickListener() }
        //        leftImg.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity)context).finish();
//            }
//        });
    }

    fun getLeftImg(): ImageView? {
        return leftImg
    }

    fun setLeftImg(leftImg: ImageView?) {
        this.leftImg = leftImg!!
    }

    fun getRightImg(): ImageView? {
        return rightImg
    }

    fun setRightImg(rightImg: ImageView?) {
        this.rightImg = rightImg!!
    }

    fun getLeftTv(): TextView? {
        return leftTv
    }

    fun setLeftTv(leftTv: TextView?) {
        this.leftTv = leftTv!!
    }

    fun getCenterTv(): TextView? {
        return centerTv
    }

    fun setCenterTv(centerTv: TextView?) {
        this.centerTv = centerTv!!
    }

    fun getRightTv(): TextView? {
        return rightTv
    }

    fun setRightTv(rightTv: TextView?) {
        this.rightTv = rightTv!!
    }

    fun getLeftImgSrc(): Int {
        return leftImgSrc
    }

    fun setLeftImgSrc(leftImgSrc: Int) {
        this.leftImgSrc = leftImgSrc
        leftImg.setImageResource(leftImgSrc)
    }

    fun getRightImgSrc(): Int {
        return rightImgSrc
    }

    fun setRightImgSrc(rightImgSrc: Int) {
        this.rightImgSrc = rightImgSrc
        rightImg.setImageResource(rightImgSrc)
    }

    fun getLeftTvContent(): String? {
        return leftTvContent
    }

    fun setLeftTvContent(leftTvContent: String?) {
        this.leftTvContent = leftTvContent!!
        leftTv.text = leftTvContent
    }

    fun getCenterTvContent(): String? {
        return centerTvContent
    }

    fun setCenterTvContent(centerTvContent: String?) {
        this.centerTvContent = centerTvContent!!
        centerTv.text = centerTvContent
    }

    fun getRightTvContent(): String? {
        return rightTvContent
    }

    fun setRightTvContent(rightTvContent: String?) {
        this.rightTvContent = rightTvContent!!
        rightTv.text = rightTvContent
    }



}