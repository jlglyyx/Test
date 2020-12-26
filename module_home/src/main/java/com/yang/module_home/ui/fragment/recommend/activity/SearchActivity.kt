package com.yang.module_home.ui.fragment.recommend.activity

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.snackbar.Snackbar
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.clicks
import com.yang.common_lib.util.showShort
import com.yang.module_home.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.act_search.*
import java.util.*


/**
 * @ClassName SearchActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 16:05
 */

@Route(path = RoutePath.HOME_SEARCH_ACTIVITY)
class SearchActivity: BaseActivity() {

    var tagList = mutableListOf<String>()
    var list = mutableListOf<String>()

    override fun getLayout(): Int {

        return R.layout.act_search

    }

    override fun initView() {
        showShort("搜索")
        initTagFlowLayout()
        initRecyclerView()
        img_delete.setOnClickListener {
            Snackbar.make(ll_search,"确定删除?",Snackbar.LENGTH_SHORT).setAction(
                "确定"
            ) {
                tagList.clear()
                initTagFlowLayout()
            }.show()

        }


        clicks(tv_search).subscribe {
            val toString = et_search.text.toString()
            if (!TextUtils.isEmpty(toString)){
                if (tagList.contains(toString)){
                    tagList.remove(toString)
                }
                tagList.add(0,toString)
                initTagFlowLayout()
            }
        }
    }

    override fun initViewModel() {


    }

    private fun initRecyclerView(){
        for (i in 1..100){
            list.add("$i")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchAdapter(R.layout.item_search,list)

    }
    private fun initTagFlowLayout(){
        if (tagList.size == 0){
            ll_history.visibility = GONE
        }else{
            ll_history.visibility = VISIBLE
        }
        flowLayout.adapter = object : TagAdapter<String>(tagList){
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val textView = LayoutInflater.from(this@SearchActivity).inflate(R.layout.view_search_tag, null, false) as  TextView
                textView.text = t!!
                return textView
            }

        }
        flowLayout.setOnSelectListener { selectPosSet -> Log.i("TAG", "onSelected: ${selectPosSet.toString()}") }
        flowLayout.setOnTagClickListener { view, position, parent ->
            Log.i("TAG", "onSelected: ${position}a")
            return@setOnTagClickListener true
        }
    }


    inner class SearchAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

        override fun convert(holder: BaseViewHolder, item: String) {


        }
    }
}