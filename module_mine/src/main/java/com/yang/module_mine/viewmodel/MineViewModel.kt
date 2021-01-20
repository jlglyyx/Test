package com.yang.module_mine.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.module_mine.data.MineItemBean
import com.yang.module_mine.data.MineItemBean.Companion.TYPE_A
import com.yang.module_mine.data.MineItemBean.Companion.TYPE_B
import com.yang.module_mine.repository.MineRepository
import javax.inject.Inject


/**
 * @ClassName HomeViewModel
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 14:26
 */
class MineViewModel @Inject constructor(
    private val mineRepository: MineRepository,
    private val mContext: Context
) : BaseViewModel() {

    companion object{
        private const val TAG = "MineViewModel"
    }


    var itemList = MutableLiveData<MutableList<MineItemBean>>().apply {
       with(mutableListOf<MineItemBean>()){
            //add(MineItemBean(null, TYPE_B))

            add(MineItemBean("下载列表",TYPE_A))
            add(MineItemBean("收藏列表",TYPE_A))

            add(MineItemBean("意见反馈",TYPE_A))
            add(MineItemBean("设置",TYPE_A))

           value = this
        }
    }


}