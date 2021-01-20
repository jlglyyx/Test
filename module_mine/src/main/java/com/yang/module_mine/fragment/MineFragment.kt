package com.yang.module_mine.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yang.common_lib.base.fragment.BaseFragment
import com.yang.common_lib.constant.RoutePath.MINE_FRAGMENT
import com.yang.common_lib.util.getRemoteComponent
import com.yang.module_mine.R
import com.yang.module_mine.data.MineItemBean
import com.yang.module_mine.di.component.DaggerMineComponent
import com.yang.module_mine.di.module.MineModule
import com.yang.module_mine.factory.MineViewModelFactory
import com.yang.module_mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fra_mine.*
import javax.inject.Inject


/**
 * @ClassName MineFragment
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/11/28 13:57
 */
@Route(path = MINE_FRAGMENT)
class MineFragment : BaseFragment() {

    @Inject
    lateinit var mineViewModelFactory: MineViewModelFactory

    lateinit var mineViewModel: MineViewModel

    private lateinit var mineFragmentAdapter:MineFragmentAdapter


    override fun getLayout(): Int {
        return R.layout.fra_mine

    }

    override fun initView() {

        recyclerView.layoutManager = object : LinearLayoutManager(requireContext()){
            override fun canScrollVertically(): Boolean {
                return true
            }
        }
        mineFragmentAdapter = MineFragmentAdapter(mineViewModel.itemList.value?: mutableListOf())
        recyclerView.adapter = mineFragmentAdapter

        mineFragmentAdapter.setOnItemClickListener { adapter, view, position ->


        }

    }



    override fun initViewModel() {

        DaggerMineComponent.builder().mineModule(MineModule(requireActivity())).remoteComponent(getRemoteComponent()).build().inject(this)

        mineViewModel = ViewModelProvider(this,mineViewModelFactory).get(MineViewModel::class.java)
    }


    inner class MineFragmentAdapter(data: MutableList<MineItemBean>) : BaseMultiItemQuickAdapter<MineItemBean, BaseViewHolder>(data) {


        init {
            addItemType(MineItemBean.TYPE_A,R.layout.item_menu)
            addItemType(MineItemBean.TYPE_B,R.layout.item_head)
        }

        override fun convert(holder: BaseViewHolder, item: MineItemBean) {

            when(item.itemType){
                MineItemBean.TYPE_A ->{
                    holder.setText(R.id.tv_content,item.content)
                }
                MineItemBean.TYPE_B ->{

                }
                MineItemBean.TYPE_C ->{

                }
            }


        }


    }

}