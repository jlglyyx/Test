package com.yang.module_video.ui.fragment.recommend.bean

import com.chad.library.adapter.base.entity.MultiItemEntity


/**
 * @ClassName RecommendTypeBean
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/3 10:23
 */
class RecommendTypeBean(override val itemType: Int) :MultiItemEntity {

    companion object{
        const val BIG_IMG_CODE = 1
        const val SMART_IMG_CODE = 2
        const val TITLE_CODE = 3
    }

    var id:String? = null
    var url:String = ""
    var imgUrl:String? = null
    var type:String? = null
    var title:String? = null
    var desc:String? = null


}