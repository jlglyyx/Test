package com.yang.common_lib.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.common_lib.util.getStatusBarHeight


/**
 * @ClassName BaseFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
abstract class BaseFragment : Fragment() {

    private var mView:View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (mView == null){
            mView = inflater.inflate(getLayout(), container, false)
            if (setStatusPadding()){
                mView?.setPadding(0, getStatusBarHeight(requireActivity()),0,0)
            }
        }
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initView()
    }


    fun <T: ViewModel> getViewModel(factory: ViewModelProvider.Factory, viewModel: Class<T>): T {

        return ViewModelProvider(this, factory).get(viewModel)
    }

    fun <T: ViewModel> getViewModel(viewModel: Class<T>): T {

        return ViewModelProvider(this).get(viewModel)
    }


    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun initViewModel()

    open fun setStatusPadding():Boolean{
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mView!=null){
            (mView?.parent as ViewGroup).removeView(mView)
        }
    }

}