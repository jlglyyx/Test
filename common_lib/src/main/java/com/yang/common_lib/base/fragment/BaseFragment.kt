package com.yang.common_lib.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * @ClassName BaseFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
open abstract class BaseFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(getLayout(),container,false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initViewModel()
    }


    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun initViewModel()
}