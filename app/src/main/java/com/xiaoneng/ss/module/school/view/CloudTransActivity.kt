package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.eventBus.FileDownloadEvent
import com.xiaoneng.ss.common.utils.eventBus.FileUploadEvent
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.school.adapter.CloudTransAdapter
import com.xiaoneng.ss.module.school.interfaces.IFileTrans
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_trans.*
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
    var mDataDownload: ArrayList<DiskFileBean> = ArrayList()
    var rotationB = false
    var mCurrent = 0
    var bean: DiskFileBean = DiskFileBean()
    private val delDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_trans
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(this).register()
    }

    override fun initView() {
        super.initView()

        tvUpload.setOnClickListener {
            mCurrent = 0
            tvUpload.setBackgroundResource(R.drawable.bac_blue_bac)
            tvUpload.setTextColor(resources.getColor(R.color.white))
            tvDownload.setBackgroundResource(R.drawable.bac_blue_line_21)
            tvDownload.setTextColor(resources.getColor(R.color.themeColor))
            mAdapter.type = 0
            rvTrans.setNewData(mData)
        }
        tvDownload.setOnClickListener {
            mCurrent = 1
            tvDownload.setBackgroundResource(R.drawable.bac_blue_bac)
            tvDownload.setTextColor(resources.getColor(R.color.white))
            tvUpload.setBackgroundResource(R.drawable.bac_blue_line_21)
            tvUpload.setTextColor(resources.getColor(R.color.themeColor))
            mAdapter.type = 1
            rvTrans.setNewData(mDataDownload)
        }
        getData()
        initAdapter()
    }


    override fun getData() {
        super.getData()
        mData.clear()
        mData.addAll(FileTransInfo.getFilesInfo())
        mDataDownload.clear()
        mDataDownload.addAll(FileDownloadInfo.getFilesInfo())
    }

    private fun initAdapter() {
        mAdapter = CloudTransAdapter(R.layout.item_cloud_trans, mData, this)
        rvTrans.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudTransActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            var bean = mAdapter.data[position]
            if (bean.status == 2) {
                if (bean.objectid.endIsImage()) {
                    mStartActivity<ImageScaleActivity>(this) {
                        putExtra(Constant.DATA, UserInfo.getUserBean().domain + bean.objectid)
                    }
                } else {
                    var filePath = bean.path
                    var filename = File(filePath)
                    if (filename.exists()) {
                        doOpen(filePath)
                    }
                }
            }
        }
        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            delDialog.show()
            true
        }
        rvTrans.notifyDataSetChanged()
    }
    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(this, filePath, null, null)
    }
    override fun upload(file: DiskFileBean) {
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
                        var bean2 = DiskFileBean(
                            token = UserInfo.getUserBean().token,
                            filename = bean.filename,
                            objectid = bean.objectKey,
                            folderid = bean.folderid
                        )
                        mViewModel.addFile(bean2)

                    }

                }

                override fun onFail() {
                    mRootView.post {
//                        mToast("文件上传失败")
                    }
                }

            })
    }

    private fun initDialog(): Dialog {
        //i: 0 新建文件夹  1 文件夹重命名 2 文件重命名
        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_new_folder, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            this.resources.displayMetrics.widthPixels - dp2px(32f).toInt()
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        var etFolderName = contentView.findViewById<EditText>(R.id.etFolderName)
        var tvTitle = contentView.findViewById<TextView>(R.id.tvTitle6)

        var tvConfirm = contentView.findViewById<TextView>(R.id.tvFolderConfirm)
        contentView.findViewById<View>(R.id.ivClose).setOnClickListener {
            bottomDialog.dismiss()
        }

        tvConfirm.setOnClickListener {
            var folderName = etFolderName.text.toString()

            if (folderName.isEmpty()) {
                mToast(R.string.lack_info)
                return@setOnClickListener
            }


            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
     fun running(task: DownloadTask) {
        Log.w("=====",task.percent.toString())
        var taskUrl = task.getKey()
        if (taskUrl.length > UserInfo.getUserBean().domain?.length ?: 0) {
            taskUrl = taskUrl.substring(UserInfo.getUserBean().domain?.length ?: 0, taskUrl.length)
        }
        var bean = DiskFileBean(
            objectid = taskUrl, progress = task.getPercent().toLong(),
            totalSize = task.fileSize, currentSize = task.fileSize * task.percent / 100
        )
        FileDownloadInfo.modifyFile(bean)
        FileDownloadEvent(bean).post()

    }

    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        //在这里处理任务完成的状态
        var taskUrl = task.getKey()
        if (taskUrl.length > UserInfo.getUserBean().domain?.length ?: 0) {
            taskUrl = taskUrl.substring(UserInfo.getUserBean().domain?.length ?: 0, taskUrl.length)
        }
        var bean = DiskFileBean(
            objectid = taskUrl, status = 2
        )
        FileDownloadInfo.modifyFile(bean)
        FileDownloadEvent(bean).post()
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
        for (i in 0..mData.size) {
            if (mData[i].objectKey == event.file.objectKey) {
                if (event.file.currentSize != 0L) {
                    mData[i].currentSize = event.file.currentSize
                }
                if (event.file.totalSize != 0L) {
                    mData[i].totalSize = event.file.totalSize
                }
                if (event.file.status != 0) {
                    mData[i].status = event.file.status
                }
                if (event.file.task != null) {
                    mData[i].task?.cancel()
                    mData[i].task = event.file.task
                }
                pp = i
                break
            }
        }
        if (mCurrent==0) {
            mAdapter.setNewData(mData)
            rvTrans.notifyDataSetChanged()
        }
//        mAdapter.notifyItemChanged(pp)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshDownloadFile(event: FileDownloadEvent) {
        var pp = 0
        for (i in 0..mDataDownload.size) {
            if (mDataDownload[i].objectid == event.file.objectid) {
                if (event.file.currentSize != 0L) {
                    mDataDownload[i].currentSize = event.file.currentSize
                }
                if (event.file.totalSize != 0L) {
                    mDataDownload[i].totalSize = event.file.totalSize
                }
                if (event.file.progress != 0L) {
                    mDataDownload[i].progress = event.file.progress
                }
                if (event.file.status != 0) {
                    mDataDownload[i].status = event.file.status
                }
                pp = i
                break
            }
        }
        if (mCurrent==1) {
            mAdapter.setNewData(mDataDownload)
            rvTrans.notifyDataSetChanged()
        }
//        mAdapter.notifyItemChanged(pp)
    }


}