package com.yang.test.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.util.io_main
import com.yang.test.api.ApiService
import javax.inject.Inject


/**
 * @ClassName RemoteViewModel
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/25 16:19
 */
class RemoteViewModel @Inject constructor(private val apiService: ApiService,private val mContext: Context): BaseViewModel() {

    val text = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun requestBD(){
        apiService.getA().compose(
            io_main(
                mContext,
                "请求中...",
                "请求成功"
            )
        )
            .subscribe({
                Log.i("TAG", "success: $it")
                text.postValue(it)
            }, {
                Log.i("TAG", "error: ${it.message}")
            })

    }
}