package com.yang.common_lib.remote.di.module

import android.util.Log
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.interceptor.UrlInterceptor
import com.yang.common_lib.scope.ActivityScope
import com.yang.common_lib.scope.RemoteScope
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {


    @RemoteScope
    @Provides
    fun provideOkHttpClient(logInterceptor: Interceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(UrlInterceptor())
            .addInterceptor(logInterceptor)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool())
            .build()

    }

    @RemoteScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @RemoteScope
    @Provides
    fun provideLogInterceptor(): Interceptor {

        return Interceptor { chain ->
            val request = chain.request()
            Log.i(TAG_LOG, "${request.url()}")
            var response = chain.proceed(request)
            response.body()?.also {
                val let = it.string().apply {
                    Log.i(TAG_LOG, this)
                }
                response = response.newBuilder().body(ResponseBody.create(it.contentType(),let)).build()
            }

            response
        }
    }


    @RemoteScope
    @Provides
    fun provideBaseApiService(retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }

    companion object {
        const val baseUrl = "https://www.baidu.com/"
        private const val TAG = "RemoteModule"
        private const val TAG_LOG = "httpLog"
        private const val CONNECT_TIMEOUT: Long = 3000
        private const val READ_TIMEOUT: Long = 3000
    }
}