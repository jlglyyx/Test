package com.yang.module_mine.fragment

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Drawable
import android.os.IBinder
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath.MINE_FRAGMENT
import com.yang.common_lib.down.DownLoadService
import com.yang.module_mine.R
import kotlinx.android.synthetic.main.fra_mine.*


/**
 * @ClassName MineFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
@Route(path = MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    var list = mutableListOf<String>()
    lateinit var mineFragmentAdapter:MineFragmentAdapter

    private lateinit var serviceConnection: ServiceConnection
    private lateinit var downLoadService : DownLoadService.DownloadBinder


    override fun getLayout(): Int {
        return R.layout.fra_mine

    }

    override fun initView() {

        initServiceConnection()

        for (i in 0..100){
            list.add("$i")
        }

        recyclerView.layoutManager = object : LinearLayoutManager(requireContext()){
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        mineFragmentAdapter = MineFragmentAdapter(R.layout.item_menu, list)
        recyclerView.adapter = mineFragmentAdapter
        val value = object : CustomTarget<Drawable>(){
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                ll_a.background = resource
            }


        }

        mineFragmentAdapter.setOnItemClickListener { adapter, view, position ->

            downLoadService.newBuilder()
                .url("https://dl.softmgr.qq.com/original/net_app/QQPhoneManager_990420.5239.exe")
                .suffix("exe")
                .childMkdirPath("exe")
                .build()
        }

        //Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607509937585&di=97b9e651110aea14947abc0a9fcf1bc8&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F07448b820b27cd7394c0836161e8e76f95d016da27e8a-LtXDaF_fw658").into(value)
        //Glide.with(this).load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2400147585,2493813740&fm=26&gp=0.jpg0").into(siv_head)
    }


    private fun initServiceConnection(){

        serviceConnection = object : ServiceConnection{
            override fun onServiceDisconnected(name: ComponentName) {
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                downLoadService = service as DownLoadService.DownloadBinder
            }

        }

        activity?.bindService(Intent(requireActivity(),DownLoadService::class.java),serviceConnection,BIND_AUTO_CREATE)
    }

    override fun initViewModel() {


    }


    inner class MineFragmentAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {


        override fun convert(holder: BaseViewHolder, item: String) {


        }


    }

}