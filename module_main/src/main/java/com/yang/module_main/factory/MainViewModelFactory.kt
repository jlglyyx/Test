package com.yang.module_main.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.module_main.repository.MainRepository
import com.yang.module_main.viewmodel.MainViewModel


/**
 * @ClassName HomeViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:09
 */
class MainViewModelFactory constructor(private val homeRepository: MainRepository, private val mContext:Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java) -> { MainViewModel(homeRepository,mContext) as T }


            else ->{
                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }
    }
}