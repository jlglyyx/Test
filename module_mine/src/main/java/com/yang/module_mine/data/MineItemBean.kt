package com.yang.module_mine.data

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @ClassName MineItemBean
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/20 10:15
 */
data class MineItemBean(var content:String? , override val itemType: Int): MultiItemEntity{

    companion object{
        const val TYPE_A = 100
        const val TYPE_B = 101
        const val TYPE_C = 102
    }
}



