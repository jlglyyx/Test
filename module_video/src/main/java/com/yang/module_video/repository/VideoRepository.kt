package com.yang.module_video.repository

import com.yang.module_video.api.VideoApiService
import com.yang.module_video.ui.fragment.recommend.bean.RecommendTypeBean
import io.reactivex.Observable
import okhttp3.RequestBody
import javax.inject.Inject


/**
 * @ClassName HomeRepository
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:22
 */
class VideoRepository @Inject constructor(private val videoApiService: VideoApiService) {

    fun getRecommendList(): Observable<MutableList<RecommendTypeBean>> {
        return videoApiService.getRecommendList()
            .map {
                if (!it.success){
                    throw Exception(it.message)
                }
                it.data
            }
    }


    fun uploadFile(files:MutableMap<String, RequestBody>) : Observable<Any>{
        return videoApiService.uploadFile(files).map {
            if (!it.success){
                throw Exception(it.message)
            }
            it.data
        }
    }

}