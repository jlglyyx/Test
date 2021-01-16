package com.yang.module_main.repository

import com.yang.common_lib.remote.di.response.MResult
import com.yang.module_main.api.MainApiService
import com.yang.module_main.data.UserBean
import com.yang.module_main.data.VideoBean
import retrofit2.http.Query
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
class MainRepository @Inject constructor(private val mainApiService: MainApiService) {

    suspend fun login(userName:String,password:String): MResult<UserBean> {

        return mainApiService.login(userName,password)
    }
    suspend fun register(userBean: UserBean): MResult<UserBean> {

        return mainApiService.register(userBean)
    }
    suspend fun splashVideo(): MResult<VideoBean> {

        return mainApiService.splashVideo()
    }


}