package com.yang.module_home.api
import com.yang.common_lib.api.BaseApiService
import io.reactivex.Observable
import retrofit2.http.GET

interface HomeApiService: BaseApiService {
    @GET("/s?ie=utf-8&f=3&rsv_bp=1&rsv_idx=1&tn=baidu&wd=library%24%7Bapplicationid%7D%E5%BC%95%E7%94%A8&fenlei=256&oq=1&rsv_pq=ebff19570002e070&rsv_t=cb39p8Zo7mq6gZsdaoP%2Fo4zxrvj5mbdIHGAWFn6xK3yUdQmGAvUU0tDxLV4&rqlang=cn&rsv_dl=th_4&rsv_enter=1&rsv_btype=t&inputT=1841&rsv_sug3=4&rsv_sug1=4&rsv_sug7=101&rsv_sug2=1&rsp=4&rsv_sug9=es_0_1&rsv_sug4=2744&rsv_sug=9")
    fun getB():Observable<String>
}