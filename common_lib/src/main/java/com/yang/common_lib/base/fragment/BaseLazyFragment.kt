package com.yang.common_lib.base.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
abstract class BaseLazyFragment : Fragment() {

    private var mView:View? = null
    private var isFirstLoad = true

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


    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun initViewModel()

    open fun setStatusPadding(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        Log.i("TAG", "onResume: $isFirstLoad")
        if (isFirstLoad) {
            isFirstLoad = false
            initData()
            //Log.i("TAG", "onResume: $isFirstLoad")
        }
    }

//    override fun onStop() {
//        super.onStop()
//        isFirstLoad = true
//    }

    override fun onPause() {
        super.onPause()
        Log.i("TAG", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i("TAG", "onStop: ")
    }


    override fun onStart() {
        super.onStart()
        Log.i("TAG", "onStart: ")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("TAG", "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("TAG", "onDetach: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mView!=null){
            (mView?.parent as ViewGroup).removeView(mView)
        }
    }

}