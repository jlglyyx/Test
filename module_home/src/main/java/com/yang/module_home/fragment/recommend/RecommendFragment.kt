package com.yang.module_home.fragment.recommend

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.yang.common_lib.base.fragment.BaseLazyFragment
import com.yang.common_lib.constant.RoutePath
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fra_home_recommend.*
import javax.inject.Inject


/**
 * @ClassName RecommendFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/1 16:04
 */
@Route(path = RoutePath.HOME_RECOMMEND_FRAGMENT)
class RecommendFragment: BaseLazyFragment() {

    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    override fun getLayout(): Int {

        return R.layout.fra_home_recommend
    }

    override fun initView() {


    }

    override fun initData() {
        homeViewModel.getB().observe(this, Observer{
            //Toast.makeText(requireContext(), it,Toast.LENGTH_SHORT).show()
            tv_text.text = it
        })
    }

    override fun initViewModel() {
        DaggerHomeComponent.builder().homeModule(HomeModule(requireActivity())).remoteComponent(
            getRemoteComponent()
        ).build().inject(this)

        homeViewModel = ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)
    }



}