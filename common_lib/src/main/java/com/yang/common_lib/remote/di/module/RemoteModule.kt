package com.yang.common_lib.remote.di.module

import android.util.Log
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.interceptor.UrlInterceptor
import com.yang.common_lib.scope.ActivityScope
import com.yang.common_lib.scope.RemoteScope
import dagger.Module
import dagger.Provides
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.StringBuilder
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
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
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

//        return Interceptor { chain ->
//            val request = chain.request()
//            Log.i(TAG_LOG, "${request.url()}")
//            var response = chain.proceed(request)
//            response.body()?.also {
//                val let = it.string().apply {
//                    Log.i(TAG_LOG, this)
//                }
//                response = response.newBuilder().body(ResponseBody.create(it.contentType(),let)).build()
//            }
//
//            response
//        }


        return  Interceptor {chain ->
                val request = chain.request()
                var response = chain.proceed(request)
                Log.d(TAG_LOG, "intercept: ===============request===============")
                Log.d(TAG_LOG, "request.baseUrl: ${request.url().toString().substring(0,(request.url().toString().length-request.url().encodedPath().length+1))}\n")
                Log.d(TAG_LOG, "request.url: ${request.url()}\n")
                Log.d(TAG_LOG, "request.method: ${request.method()}\n")
                Log.d(TAG_LOG, "request.headers: ${request.headers()}\n")
                Log.d(TAG_LOG, "request.body: ${getBody(request)}\n")
                Log.d(TAG_LOG, "intercept: ===============request===============")
                Log.d(TAG_LOG, "intercept: ===============response===============")
                Log.d(TAG_LOG, "response.isSuccessful: ${response.isSuccessful}\n")
                Log.d(TAG_LOG, "response.message: ${response.message()}\n")
                Log.d(TAG_LOG, "response.headers: ${response.headers()}\n")
                Log.d(TAG_LOG, "response.code: ${response.code()}\n")
                val content = response.body()?.string()
                val contentType = response.body()?.contentType()
                //Log.d(TAG_LOG, "response.body: ${content?.length} ${content}\n")
                showLogCompletion(content.toString(),3000)
                Log.d(TAG_LOG, "response.request.url: ${response.request().url()}\n")
                Log.d(TAG_LOG, "intercept: ===============response===============")
                response = response.newBuilder().body(ResponseBody.create(contentType,content)).build()
                response
            }
    }


    @RemoteScope
    @Provides
    fun provideBaseApiService(retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }

    companion object {
        const val baseUrl = "http://jlgl.free.idcfengye.com/"
        private const val TAG = "RemoteModule"
        private const val TAG_LOG = "httpLog"
        private const val CONNECT_TIMEOUT: Long = 8000
        private const val READ_TIMEOUT: Long = 8000
        private const val WRITE_TIMEOUT: Long = 8000
    }

    private fun getBody(request: Request):String{
        val buffer = Buffer()
        val body = request.body()
        body?.writeTo(buffer)
        val contentType = body?.contentType()
        val charset = contentType?.charset(Charsets.UTF_8)
        return if (charset != null) {
            buffer.readString(charset)
        }else{
            "无请求体"
        }
    }

    private fun showLogCompletion(log:String,size:Int ){

        if (log.length > size){
            val substring = log.substring(0, size)
            Log.d(TAG_LOG, "response.body: ${substring}")
            if (log.length - substring.length > size){
                val substring1 = log.substring(substring.length, log.length)
                showLogCompletion(substring1,size)
            }else{
                val substring1 = log.substring(substring.length, log.length)
                Log.d(TAG_LOG, "${substring1}")
            }

        }else{
            Log.d(TAG_LOG, "response.body: ${log}\n")
        }

    }



}