package com.xiaoneng.ss.module.school.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.module.school.adapter.DiskAdapter
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_disk.*
import java.io.File


/**
 * @author Burning
 * @description:教学云盘
 * @date :2020/10/23 3:17 PM
 */
class CloudDiskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: DiskAdapter
    var mData: ArrayList<DiskFileBean> = ArrayList()
    var rotationB = false

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_disk
    }

    override fun initView() {
        super.initView()
        ivAddFile.setOnClickListener {
            val anim: ObjectAnimator
            if (rotationB) {
                anim = ObjectAnimator.ofFloat(it, "rotation", 45.0F, 0F)
                anim.doOnEnd {
                tvDiskNew.visibility = View.GONE
                tvDiskUpload.visibility = View.GONE
                }
            } else {
                anim = ObjectAnimator.ofFloat(it, "rotation", 0.0F, 45.0F)
                anim.doOnEnd {
                tvDiskNew.visibility = View.VISIBLE
                tvDiskUpload.visibility = View.VISIBLE
                }
            }
            rotationB = !rotationB
            anim.duration = 300
            anim.start()
        }
        tvDiskUpload.setOnClickListener { }
        tvDiskNew.setOnClickListener { }
        initAdapter()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getBookList("")
    }

    private fun initAdapter() {

        mAdapter = DiskAdapter(R.layout.item_disk, mData)
        rvDisk.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudDiskActivity)
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
}