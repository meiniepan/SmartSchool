package com.xiaoneng.ss.module.school.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.getOssObjectKey
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.DiskAdapter
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.model.FileExtBean
import com.xiaoneng.ss.module.school.model.FileInfoBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_cloud_disk.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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
    var filePath: String? = null
    var fileName: String? = null

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
        tvDiskUpload.setOnClickListener { choseFile() }
        tvDiskNew.setOnClickListener { }
        ivFileRecord.setOnClickListener {
            mStartActivity<CloudTransActivity>(this)
        }
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

    private fun doUpload(it: StsTokenResp) {

        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName
        //将上传文件的进度信息存储到本地
        var objectKey =
            getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
            this,
            it.Credentials,
            objectKey,
            filePath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {


                    }

                }

                override fun onFail() {
                    mRootView.post {
                        mToast("文件上传失败")
                    }
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) { //是否选择，没选择就不会继续
            val uri: Uri? = data?.getData() //得到uri，后面就是将uri转化成file的过程。
            filePath = OssUtils().getPath(this, uri)
            fileName = filePath?.split("/")?.last()
            if (!filePath.isNullOrEmpty()) {
                mViewModel.getSts()
            } else {
                mToast(getString(R.string.errFile))
            }
        }

    }

    override fun initDataObserver() {
        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {
                Handler().postDelayed(
                    {
                        GlobalScope.launch() {
                            async {
                                doUpload(it)
                            }
                        }
                    }, 100
                )

            }
        })
    }
}