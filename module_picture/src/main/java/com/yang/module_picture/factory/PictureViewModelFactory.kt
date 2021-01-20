package com.yang.module_picture.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.module_picture.repository.PictureRepository
import com.yang.module_picture.viewmodel.PictureViewModel


/**
 * @ClassName HomeViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:09
 */
class PictureViewModelFactory constructor(private val pictureRepository: PictureRepository, private val mContext:Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return when{
            modelClass.isAssignableFrom(PictureViewModel::class.java) -> { PictureViewModel(pictureRepository,mContext) as T }


            else ->{
                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }
    }
}