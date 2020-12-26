package com.yang.module_main.viewmodel

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tencent.bugly.proguard.s
import com.yang.common_lib.base.viewmodel.BaseViewModel
import com.yang.common_lib.dialog.RemoteMessageDialog
import com.yang.common_lib.handle.ErrorHandle
import com.yang.common_lib.util.io_main
import com.yang.common_lib.util.remoteMessageDialog
import com.yang.module_main.data.UserBean
import com.yang.module_main.repository.MainRepository
import kotlinx.coroutines.*
import javax.inject.Inject


/**
 * @ClassName HomeViewModel
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 14:26
 */
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val mContext: Context
) : BaseViewModel() {

    companion object{
        private const val TAG = "MainViewModel"
    }

    private var userInfo = MutableLiveData<UserBean>()
    fun login(): MutableLiveData<UserBean> {
        val remoteMessageDialog = RemoteMessageDialog(mContext, "登录中")
        viewModelScope.launch {
            remoteMessageDialog.show()
            val async = viewModelScope.async(Dispatchers.IO) {
                try {
                    mainRepository.login().run {
                        if (this.success) {
                            userInfo.postValue(this.data)
                            "请求成功"
                        }else{
                            this.message
                        }
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "login: $e")
                    ErrorHandle(e).handle()
                    "登录失败"
                }
            }
            remoteMessageDialog.content = async.await()
            delay(500)
            remoteMessageDialog.dismiss()
        }
        return userInfo
    }

}