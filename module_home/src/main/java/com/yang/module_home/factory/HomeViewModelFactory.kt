package com.yang.module_home.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.module_home.repository.HomeRepository
import com.yang.module_home.viewmodel.HomeViewModel


/**
 * @ClassName HomeViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:09
 */
class HomeViewModelFactory constructor(private val homeRepository: HomeRepository,private val mContext:Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> { HomeViewModel(homeRepository,mContext) as T }


            else ->{
                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }
    }
}