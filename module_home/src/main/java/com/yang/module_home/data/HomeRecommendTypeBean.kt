package com.yang.module_home.data

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @ClassName HomeRecommendTypeBean
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2021/1/21 17:13
 */
class HomeRecommendTypeBean(override val itemType: Int) : MultiItemEntity {

    companion object{
        const val TYPE_PICTURE_CODE = 1
        const val TYPE_VIDEO_CODE = 2
    }

    var id:String? = null
    var url:String = ""
    var imgUrl:String? = null
    var type:String? = null
    var title:String? = null
    var desc:String? = null


}