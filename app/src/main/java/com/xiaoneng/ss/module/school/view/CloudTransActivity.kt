package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.eventBus.FileUploadEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.module.school.adapter.CloudTransAdapter
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_trans.*
import kotlinx.android.synthetic.main.fragment_circular.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


/**
 * @author Burning
 * @description:教学云盘传输列表
 * @date :2021/03/10 3:17 PM
 */
class CloudTransActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: CloudTransAdapter
    var mData: ArrayList<DiskFileBean> = ArrayList()
    var rotationB = false

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_trans
    }

    override fun initView() {
        super.initView()

        tvUpload.setOnClickListener { }
        tvDownload.setOnClickListener { }
        initAdapter()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mData.add(DiskFileBean(progress=30))
        rvTrans.notifyDataSetChanged()
    }

    private fun initAdapter() {

        mAdapter = CloudTransAdapter(R.layout.item_cloud_trans, mData)
        rvTrans.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudTransActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    private fun choseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*") //设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) { //是否选择，没选择就不会继续
            val uri: Uri = data?.getData()!! //得到uri，后面就是将uri转化成file的过程。
            val img_path: String = OssUtils().getPath(this, uri)
            val file = File(img_path)
            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun initDataObserver() {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshFile(event: FileUploadEvent) {
        mData.forEach {
            if (it.path == event.file.path){
                it.currentSize = event.file.currentSize
                it.totalSize = event.file.totalSize
                return@forEach
            }
        }
        rvTrans.notifyDataSetChanged()
    }
}