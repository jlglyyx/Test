package com.yang.module_home.api
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.remote.di.response.Result
import com.yang.module_home.ui.fragment.recommend.bean.RecommendTypeBean
import io.reactivex.Observable
import retrofit2.http.GET

interface HomeApiService: BaseApiService {
    @GET("/test/json/main.json")
    fun getRecommendList():Observable<Result<MutableList<RecommendTypeBean>>>
}