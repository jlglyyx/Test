package com.yang.module_home.fragment.recommend.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.getPath
import com.yang.common_lib.util.showShort
import com.yang.module_home.R
import com.yang.module_home.fragment.recommend.RecommendFragment
import com.yang.module_home.fragment.recommend.bean.CommentBean
import com.yang.module_home.fragment.recommend.bean.RecommendTypeBean
import kotlinx.android.synthetic.main.act_video_paly.*
import kotlinx.android.synthetic.main.act_video_paly.recyclerView
import kotlinx.android.synthetic.main.view_public_normal_recycler_view.*


/**
 * @ClassName VideoPlayActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/2 14:05
 */
@Route(path = RoutePath.HOME_VIDEOPLAY_ACTIVITY)
class VideoPlayActivity : BaseActivity() {

    var orientationUtils: OrientationUtils?=null
    private var isPause = true
    private var isPlay = true
    lateinit var videoCommentAdapter:VideoCommentAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var  list:MutableList<CommentBean>

    companion object {
        const val FILE_CODE = 100
    }

    @Autowired
    lateinit var url: String

    override fun getLayout(): Int {
        return R.layout.act_video_paly
    }

    override fun initView() {
        initPermission()
        initRecyclerView()
        tv_test.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" //无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, FILE_CODE)
        }

        tv_commit.setOnClickListener {
            if (TextUtils.isEmpty(et_url.text.toString())){
                showShort("url为空")
                return@setOnClickListener
            }
            url = et_url.text.toString()
            initVideo()
             list.add(0,CommentBean("李四",et_url.text.toString()))
            videoCommentAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(0)
            et_url.setText("")
            showShort("成功")
        }
    }

    override fun initViewModel() {
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("CheckResult")
    private fun initPermission() {
        RxPermissions(this)
            .requestEachCombined(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe { permission ->
                when {
                    permission.granted -> {
                        initVideo()
                    }
                    permission.shouldShowRequestPermissionRationale -> {
                        showShort("请打开权限")
                    }

                    else -> {
                        showShort("请打开权限")
                    }
                }
            }
    }

    private fun initVideo() {
        detailPlayer.backButton.setOnClickListener {
            finish()
        }
//        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, detailPlayer)
        orientationUtils?.isEnable = false
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //imageView.setImageResource(R.mipmap.ic_launcher)
        Log.i("sssssssss", "initVideo: $url")
        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
            .setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle("测试视频")
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onQuitFullscreen(url, *objects)
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[0]) //title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[1]) //当前非全屏player
                    orientationUtils?.backToProtVideo()
                }
            }).setLockClickListener { view, lock ->
                orientationUtils?.isEnable = !lock
            }.build(detailPlayer)

        detailPlayer.fullscreenButton.setOnClickListener { //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer.startWindowFullscreen(mContext, true, true)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.currentPlayer.release()
        }
        orientationUtils?.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data.data as Uri
            val path = getPath(this, uri)
            Log.i("TAG", "onActivityResult: ${uri.path} ${path.toString()}")
            url = path.toString()
            initVideo()

        }
    }





    private fun initRecyclerView() {
        list = mutableListOf()
        for (i in 0..30){
            list.add(CommentBean("张 $i","这电影好看"))
        }
        videoCommentAdapter = VideoCommentAdapter(R.layout.item_recommend_comment, list)
        recyclerView.adapter = videoCommentAdapter
        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        videoCommentAdapter.setOnItemClickListener { adapter, view, position ->

            //showShort(position)
        }

    }

    inner class VideoCommentAdapter(layoutResId: Int, data: MutableList<CommentBean>?) :
        BaseQuickAdapter<CommentBean, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: CommentBean) {
            holder.setText(R.id.tv_user_name,item.userName)
                .setText(R.id.tv_comment,item.comment)
            val siv_head = holder.getView<ShapeableImageView>(R.id.siv_head)
            Glide.with(siv_head).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606997299248&di=8612164dc54681b1fcd44f765f69cdfa&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201906%2F08%2F20190608005627_dxdnb.thumb.400_0.jpg").into(siv_head)

        }


    }

}