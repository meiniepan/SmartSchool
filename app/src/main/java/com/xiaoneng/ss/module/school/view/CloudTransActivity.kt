package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.eventBus.FileUploadEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.getOssObjectKey
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.CloudTransAdapter
import com.xiaoneng.ss.module.school.interfaces.IFileTrans
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_trans.*
import kotlinx.android.synthetic.main.fragment_circular.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


/**
 * @author Burning
 * @description:教学云盘传输列表
 * @date :2021/03/10 3:17 PM
 */
class CloudTransActivity : BaseLifeCycleActivity<SchoolViewModel>(), IFileTrans {
    lateinit var mAdapter: CloudTransAdapter
    var mData: ArrayList<DiskFileBean> = ArrayList()
    var rotationB = false
    var bean:DiskFileBean = DiskFileBean()

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
        mData.clear()
        mData.addAll(FileTransInfo.getFilesInfo())
        rvTrans.notifyDataSetChanged()
    }

    private fun initAdapter() {

        mAdapter = CloudTransAdapter(R.layout.item_cloud_trans, mData,this)
        rvTrans.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudTransActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun upload(file:DiskFileBean){
        if (!file.path.isNullOrEmpty()) {
            bean = file
            mViewModel.getSts()
        } else {
            mToast(getString(R.string.errFile))
        }
    }

    private fun doUpload(it: StsTokenResp) {
        //将上传文件的进度信息存储到本地

        OssUtils.uploadResumeFile(
            this,
            it.Credentials,
            bean.objectKey,
            bean.path,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {


                    }

                }

                override fun onFail() {
                    mRootView.post {
//                        mToast("文件上传失败")
                    }
                }

            })
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshFile(event: FileUploadEvent) {
        var pp = 0
        for (i in 0..mData.size){
            if (mData[i].objectKey == event.file.objectKey){
                if (event.file.currentSize!=0L){
                    mData[i].currentSize = event.file.currentSize
                }
                if (event.file.totalSize!=0L){
                    mData[i].totalSize = event.file.totalSize
                }
                if (event.file.status!=0){
                    mData[i].status = event.file.status
                }
                if (event.file.task!=null){
                    mData[i].task?.cancel()
                    mData[i].task = event.file.task
                }
                pp = i
                break
            }
        }

        FileTransInfo.modifyFilesInfo(mData)
        mAdapter.notifyItemChanged(pp)
    }


}