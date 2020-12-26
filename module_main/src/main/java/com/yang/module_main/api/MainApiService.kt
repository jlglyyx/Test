package com.yang.module_main.api
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.remote.di.response.MResult
import com.yang.module_main.data.UserBean
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApiService: BaseApiService {

    @GET("/test/json/user.json")
    suspend fun login():MResult<UserBean>

}