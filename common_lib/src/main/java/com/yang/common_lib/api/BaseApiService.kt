package com.yang.common_lib.api

import io.reactivex.Observable
import retrofit2.http.GET

interface BaseApiService {

    @GET("/")
    fun getA():Observable<String>
}