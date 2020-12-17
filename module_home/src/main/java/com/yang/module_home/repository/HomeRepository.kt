package com.yang.module_home.repository

import com.yang.module_home.api.HomeApiService
import com.yang.module_home.ui.fragment.recommend.bean.RecommendTypeBean
import io.reactivex.Observable
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
class HomeRepository @Inject constructor(private val homeApiService: HomeApiService) {

    fun getRecommendList(): Observable<MutableList<RecommendTypeBean>> {
        return homeApiService.getRecommendList()
            .map {
                if (!it.success){
                    throw Exception(it.message)
                }
                it.data
            }
    }

}