package com.yang.module_video.ui.fragment.recommend.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.getRemoteComponent
import com.yang.common_lib.util.showShort
import com.yang.module_video.R
import com.yang.module_video.di.component.DaggerVideoComponent
import com.yang.module_video.di.module.VideoModule
import com.yang.module_video.ui.fragment.recommend.bean.CommentBean
import com.yang.module_video.ui.fragment.recommend.bean.RecommendTypeBean
import com.yang.module_video.ui.fragment.recommend.bean.VideoCollectionBean
import kotlinx.android.synthetic.main.act_video_paly.*
import kotlinx.android.synthetic.main.act_video_paly.recyclerView
import kotlinx.android.synthetic.main.act_video_paly.tabLayout
import tv.danmaku.ijk.media.player.IjkMediaPlayer
import javax.inject.Inject


/**
 * @ClassName VideoPlayActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/2 14:05
 */
@Route(path = RoutePath.VIDEO_VIDEO_PLAY_ACTIVITY)
class VideoPlayActivity : BaseActivity() {

    var orientationUtils: OrientationUtils? = null
    private var isPause = true
    private var isPlay = true
    lateinit var videoCommentAdapter: VideoCommentAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var list: MutableList<CommentBean>
    private var isRecyclerViewScroll = false
    val titles = mutableListOf<String>()
    @Inject
    lateinit var gson: Gson

    companion object {
        const val FILE_CODE = 100
    }

    @Autowired(name = "recommendTypeBean")
    lateinit var recommendTypeBeanGson: String
    lateinit var recommendTypeBean: RecommendTypeBean

    override fun getLayout(): Int {
        return R.layout.act_video_paly
    }

    override fun initView() {
        recommendTypeBean = gson.fromJson(recommendTypeBeanGson, RecommendTypeBean::class.java)
        initPermission()
        initTabLayout()
        initRecyclerView()
        initRecyclerViewCollection()
        tv_commit.setOnClickListener {
            if (TextUtils.isEmpty(et_url.text.toString())) {
                showShort("url为空")
                return@setOnClickListener
            }
            initVideo(et_url.text.toString())
            list.add(0, CommentBean("李四", et_url.text.toString()))
            videoCommentAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(0)
            et_url.setText("")
            showShort("成功")
        }
    }

    override fun initViewModel() {
        ARouter.getInstance().inject(this)
        DaggerVideoComponent.builder().videoModule(VideoModule(this)).remoteComponent(
            getRemoteComponent()
        ).build().inject(this)
    }






    @SuppressLint("ClickableViewAccessibility")
    private fun initTabLayout() {
        titles.add("视频")
        titles.add("评论")
        titles.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {


            }

            override fun onTabSelected(tab: TabLayout.Tab) {
//                    when (tab.position){
//                        0 -> {
//
//                                linearLayoutManager.scrollToPositionWithOffset(0,0)
//                            }
//                        1 -> {
//                                linearLayoutManager.scrollToPositionWithOffset(10,0)
//                        }
//                    }

                isRecyclerViewScroll = true

            }


        })

    }




    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        list = mutableListOf()
        for (i in 0..30) {
            list.add(CommentBean("张 $i", "这电影好看"))
        }
        videoCommentAdapter = VideoCommentAdapter(R.layout.item_recommend_comment, list)
        recyclerView.adapter = videoCommentAdapter
        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        videoCommentAdapter.setOnItemClickListener { adapter, view, position ->

            //showShort(position)
        }

        recyclerView.setOnTouchListener { v, event ->

            if(event.action == MotionEvent.ACTION_DOWN){

                isRecyclerViewScroll = true
            }

            return@setOnTouchListener false
        }

        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                Log.i("adkd", "onScrolled: $findFirstVisibleItemPosition")
                if (isRecyclerViewScroll){
                    if (findFirstVisibleItemPosition >= 1){
                        with(tabLayout.getTabAt(1)){
                            if (!this?.isSelected!!){
                                this.select()
                            }
                        }
                    }else{
                        with(tabLayout.getTabAt(0)){
                            if (!this?.isSelected!!){
                                this.select()
                            }
                        }
                    }
                }

            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }



    private fun initRecyclerViewCollection() {
        val collection = mutableListOf<VideoCollectionBean>()
        for (i in 1..30) {
            if (i==1){
                collection.add(VideoCollectionBean("$i",true,"https://www.w3schools.com/html/movie.mp4"))
                continue
            }
            collection.add(VideoCollectionBean("$i",false,"https://media.w3.org/2010/05/sintel/trailer.mp4"))
        }
        val inflate = LayoutInflater.from(this).inflate(R.layout.view_video_head, null, false)

        val videoCommentCollectionAdapter = VideoCommentCollectionAdapter(R.layout.item_video_collection, collection)
        videoCommentAdapter.addHeaderView(inflate)


        val recyclerView_collection = inflate.findViewById<RecyclerView>(R.id.recyclerView_collection)
        val cl_introduction = inflate.findViewById<ConstraintLayout>(R.id.cl_introduction)

        cl_introduction.setOnClickListener {
            initIntroductionDialog()
        }




        recyclerView_collection.adapter = videoCommentCollectionAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_collection.layoutManager = linearLayoutManager
        videoCommentCollectionAdapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data as MutableList<VideoCollectionBean>

            data.forEach {
                it.select = false
            }
            val videoCollectionBean = data[position]
            videoCollectionBean.select = true
            adapter.notifyDataSetChanged()
            initVideo(videoCollectionBean.videoUrl)
            //showShort(position)
        }



    }



    private fun initIntroductionDialog(){

        AlertDialog.Builder(this)
            .setMessage("9.6分·VIP·更新至50集·全78集·7.5亿次播放9.6分·VIP·更新至50集·全78集·7.5亿次播放9.6分·VIP·更新至50集·全78集·7.5亿次播放9.6分·VIP·更新至50集·全78集·7.5亿次播放9.6分·VIP·更新至50集·全78集·7.5亿次播放")
            .show()
        with(ll_video){
            post {
                Log.i("sdfjabfn", "initIntroductionDialog: $height")
            }
        }

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
                        initVideo(recommendTypeBean.url)
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



    private fun initVideo(url:String) {
        //切换绘制模式
        GSYVideoType.setRenderType(GSYVideoType.SUFRACE)

        //ijk关闭log
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
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
            .setCacheWithPlay(true)
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
        detailPlayer.startPlayLogic()
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
            //val path = getPath(this, uri)
//            Log.i("TAG", "onActivityResult: ${uri.path} ${path.toString()}")
//            initVideo(path.toString())

        }
    }








    inner class VideoCommentAdapter(layoutResId: Int, data: MutableList<CommentBean>?) :
        BaseQuickAdapter<CommentBean, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: CommentBean) {
            holder.setText(R.id.tv_user_name, item.userName)
//                .setText(R.id.tv_comment, item.comment)
            val siv_head = holder.getView<ShapeableImageView>(R.id.siv_head)
            Glide.with(siv_head)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606997299248&di=8612164dc54681b1fcd44f765f69cdfa&imgtype=0&src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201906%2F08%2F20190608005627_dxdnb.thumb.400_0.jpg")
                .into(siv_head)

        }


    }
    inner class VideoCommentCollectionAdapter(layoutResId: Int, data: MutableList<VideoCollectionBean>?) :
        BaseQuickAdapter<VideoCollectionBean, BaseViewHolder>(layoutResId, data) {
        override fun convert(holder: BaseViewHolder, item: VideoCollectionBean) {

            val tv_collection = holder.getView<TextView>(R.id.tv_collection)
            if (item.select){
                tv_collection.setTextColor(Color.parseColor("#03DAC5"))
            }else{
                tv_collection.setTextColor(Color.BLACK)
            }
            holder.setText(R.id.tv_collection,item.collection)
        }


    }




}