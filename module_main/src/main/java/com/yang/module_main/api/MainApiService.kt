package com.yang.module_main.api
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.remote.di.response.MResult
import com.yang.module_main.data.UserBean
import com.yang.module_main.data.VideoBean
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainApiService: BaseApiService {

    @POST("/user/queryUser")
    suspend fun login(@Query("userName") userName:String,@Query("password") password:String):MResult<UserBean>

    @POST("/user/insertUser")
    suspend fun register(@Body userBean:UserBean):MResult<UserBean>


    @POST("/splashVideo")
    suspend fun splashVideo():MResult<VideoBean>

}