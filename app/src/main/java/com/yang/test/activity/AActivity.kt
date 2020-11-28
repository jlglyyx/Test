package com.yang.test.activity

import android.annotation.SuppressLint
import android.util.Log
import com.yang.common_lib.api.BaseApiService
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.util.getRemoteComponent
import com.yang.test.R
import com.yang.test.di.component.DaggerIActivityComponent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Retrofit
import java.lang.Thread.sleep
import javax.inject.Inject

class AActivity : BaseActivity() {
    companion object {
        private const val TAG = "AActivity"
    }

    @Inject
    lateinit var retrofit: Retrofit

    override fun getLayout(): Int {
        return R.layout.dialog_remote_message
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        DaggerIActivityComponent.builder().remoteComponent(getRemoteComponent()).build()
            .inject(this)

        GlobalScope.launch(Dispatchers.Unconfined) {
            delay(100)
            launch(Dispatchers.Main) {
                finish()
                Log.i(TAG, "sssssssssssss: 协程运行关闭")
            }
            Log.i(TAG, "sssssssssssss: 协程运行")
        }

        Thread {
            sleep(200)
            finish()
            Log.i(TAG, "sssssssssssss: 线程运行")
        }.start()


        var i = 0
        Observable.range(1, 1000).subscribe({

            Log.i(TAG, "sssssssssssss: ${i++}")
            retrofit.create(BaseApiService::class.java).getA()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i(TAG, "success: $it")
                }, {
                    Log.i(TAG, "error: ${it.message}")
                })
        }, {

        })


        GlobalScope.launch {
           repeat(1000){
               retrofit.create(BaseApiService::class.java).getA()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe({
                       Log.i(TAG, "success: $it")
                   }, {
                       Log.i(TAG, "error: ${it.message}")
                   })
           }
        }
    }

    override fun initViewModel() {


    }
}