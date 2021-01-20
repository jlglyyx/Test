package com.yang.module_mine.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.module_mine.repository.MineRepository
import com.yang.module_mine.viewmodel.MineViewModel


/**
 * @ClassName HomeViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:09
 */
class MineViewModelFactory constructor(private val mineRepository: MineRepository, private val mContext:Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return when{
            modelClass.isAssignableFrom(MineViewModel::class.java) -> { MineViewModel(mineRepository,mContext) as T }


            else ->{
                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }
    }
}