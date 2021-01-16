package com.yang.module_home.api
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.remote.di.response.MResult
import com.yang.module_home.ui.fragment.recommend.bean.RecommendTypeBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface HomeApiService: BaseApiService {
    @GET("/json/main.json")
    fun getRecommendList():Observable<MResult<MutableList<RecommendTypeBean>>>



    @Multipart
    @POST("/uploadFile")
    fun uploadFile(@PartMap files:MutableMap<String,RequestBody>):Observable<MResult<Any>>
}