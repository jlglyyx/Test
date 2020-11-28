package com.yang.test.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.test.api.ApiService
import com.yang.test.viewmodel.RemoteViewModel


/**
 * @ClassName MainViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/25 16:36
 */
class MainViewModelFactory constructor(private val apiService: ApiService,private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when{
            modelClass.isAssignableFrom(RemoteViewModel::class.java) -> RemoteViewModel(apiService,context) as T
            else -> {

                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }

    }
}