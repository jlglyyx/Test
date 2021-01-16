package com.yang.module_home.ui.fragment.recommend.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapePathModel
import com.google.android.material.shape.TriangleEdgeTreatment
import com.google.android.material.snackbar.Snackbar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yang.common_lib.base.activity.BaseActivity
import com.yang.common_lib.helper.ItemTouchHelperCallback
import com.yang.common_lib.helper.MoveAndSwipedListener
import com.yang.common_lib.interceptor.UrlInterceptor
import com.yang.common_lib.util.*
import com.yang.common_lib.widget.MToolbarView
import com.yang.module_home.R
import com.yang.module_home.di.component.DaggerHomeComponent
import com.yang.module_home.di.module.HomeModule
import com.yang.module_home.factory.HomeViewModelFactory
import com.yang.module_home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.act_upload.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject


/**
 * @ClassName UploadFileActivity
 *
 * @Description
 *
 * @Author 1
 *
 * @Date 2020/12/27 14:38
 */
class UploadFileActivity : BaseActivity() {

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    lateinit var homeViewModel: HomeViewModel

    private lateinit var list:MutableList<String>
    private var mList = mutableListOf<String>()
    private lateinit var adapter:UploadPathAdapter

    var filePath: String? = null

    companion object {
        const val FILE_CODE = 100
        private const val TAG = "UploadFileActivity"
    }

    override fun getLayout(): Int {

        return R.layout.act_upload
    }

    override fun initView() {
        initPermission()
        fab_bottom_appbar.bitmapResource = R.drawable.img_delete
        clicks(fab_bottom_appbar).subscribe {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, FILE_CODE)
        }

        mToolbarView.setOnItemClick(object :MToolbarView.OnItemClick{
            override fun onLeftClickListener() {
            }

            override fun onRightClickListener() {
                if (list.size <= 0) {
                    return
                }
                val mutableMapOf = mutableMapOf<String, RequestBody>()
                list.forEach {
                    val file = File(it)
                    val encode = URLEncoder.encode("${System.currentTimeMillis()}_${file.name}", "UTF-8")
                    val create = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                    mutableMapOf["file\";filename=\"$encode"] = create
                }
                UrlInterceptor.url = "http://192.168.31.61:20001/"
                homeViewModel.uploadFile(mutableMapOf)

            }
        })

        initRecyclerView()
    }

    override fun initViewModel() {

        DaggerHomeComponent.builder().homeModule(HomeModule(this)).remoteComponent(
            getRemoteComponent()
        ).build().inject(this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }


    @SuppressLint("CheckResult")
    private fun initPermission() {
        RxPermissions(this)
            .requestEachCombined(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe { permission ->
                when {
                    permission.granted -> {

                    }
                    permission.shouldShowRequestPermissionRationale -> {
                        showShort("请打开权限")
                    }

                    else -> {
                        showShort("请打开权限")
                    }
                }
            }
    }


    private fun initRecyclerView(){
        list = mutableListOf()
//        for (i in 0 until  30){
//            list.add("测试路径")
//        }
        mList.addAll(list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UploadPathAdapter(R.layout.item_upload_path,list)
        recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(recyclerView)


        val size: Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            32f,
            resources.displayMetrics
        )
        val triangleEdgeTreatment = TriangleEdgeTreatment(size, true)
        val shapePathModel = ShapeAppearanceModel()
        shapePathModel.toBuilder().setTopEdge(triangleEdgeTreatment).build()
        val materialShapeDrawable = MaterialShapeDrawable(shapePathModel)
        materialShapeDrawable.setTint(-0x222223)
        bottom_App_bar.background = materialShapeDrawable
    }



    inner class UploadPathAdapter(layoutResId: Int, data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data),MoveAndSwipedListener {


        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tv_upload_path,item)
        }

        override fun onDelete(index: Int) {
            data.removeAt(index)
            notifyItemRemoved(index)
//            Snackbar.make(coordinatorLayout_upload,"是否取消删除？",Snackbar.LENGTH_SHORT).setAction("确定") {
//                data.add(index, mList[index])
//                notifyItemInserted(index)
//            }.show()

        }

        override fun onMoved(startIndex: Int, endIndex: Int) {
            Collections.swap(data,startIndex,endIndex)
            notifyItemMoved(startIndex,endIndex)

        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {

            val uri = data.data

            try {
                filePath = FileUtil.getPath(this, uri!!)
                filePath?.let {
                    list.add(it)
                    mList.add(it)
                    adapter.notifyDataSetChanged()
                }
                Log.i(TAG, "path:$filePath")
            } catch (e: Exception) {
                Log.i(TAG, "Exception: ${e.message}")
            }

        }
    }



}