package com.yang.module_video.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.module_video.repository.VideoRepository
import com.yang.module_video.viewmodel.VideoViewModel


/**
 * @ClassName HomeViewModelFactory
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 15:09
 */
class VideoViewModelFactory constructor(private val videoRepository: VideoRepository, private val mContext:Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return when{
            modelClass.isAssignableFrom(VideoViewModel::class.java) -> { VideoViewModel(videoRepository,mContext) as T }


            else ->{
                ViewModelProvider.NewInstanceFactory().create(modelClass)
            }
        }
    }
}