package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.google.gson.Gson
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.school.model.FileExtBean
import com.xiaoneng.ss.module.school.model.FileInfoBean
import com.xiaoneng.ss.module.school.model.LogBean
import com.xiaoneng.ss.module.school.model.TaskLogRequest
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_log.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddLogActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    lateinit var taskBean: LogBean
    var filePath: String? = null
    var fileName: String? = null
    lateinit var mAdapterFile: NoticeFileAdapter
    var mDataFile = ArrayList<FileInfoBean>()
    var idString = ""
    var fileNum = 0
    var downloadNum = 0

    override fun getLayoutId(): Int = R.layout.activity_add_log

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(this).register()
    }

    override fun initView() {
        super.initView()
        taskBean = intent.getParcelableExtra(Constant.DATA)!!
        rvConfirm.setOnClickListener {
            if (etFeedback.text.toString().isNullOrEmpty()) {
                mToast(R.string.lack_info)
                return@setOnClickListener
            }
            var bean = TaskLogRequest(
                UserInfo.getUserBean().token,
                taskBean.id ?: "",
                etFeedback.text.toString(),
                completestatus = "1",
                fileinfo = Gson().toJson(mDataFile)
            )
            mViewModel.modifyTaskInfo(bean)
        }

        tvUpFile.apply {
            setOnClickListener {
                choseFile()
            }
        }

        etFeedback.setText(taskBean.feedback)
        initAdapterFile()
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }

    private fun initAdapterFile() {
        rvTaskFile.visibility = View.GONE
        mAdapterFile = NoticeFileAdapter(R.layout.item_notice_file, mDataFile)
        rvTaskFile.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapterFile)
        }
        mAdapterFile.setOnItemClickListener { _, view, position ->
            if (mDataFile[position].url.endIsImage()) {
                mStartActivity<ImageScaleActivity>(this) {
                    putExtra(Constant.DATA, UserInfo.getUserBean().domain + mDataFile[position].url)
                }
            } else {
                var path = PathSelector(BaseApplication.instance).getDownloadsDirPath()
                var name = idString + mDataFile[position].name
                var filePath = path + File.separator + name
                var filename = File(filePath)
                if (filename.exists()) {
                    doOpen(filePath)
                } else {
                    doDown(mDataFile[position].url, name)
                }
            }
        }
    }

    private fun doDown(url: String?, fileName: String?) {
        showLoading()
        val url = UserInfo.getUserBean().domain + url
        //获取应用外部照片储存路径
        val filePath =
            PathSelector(BaseApplication.instance).getDownloadsDirPath() + File.separator + fileName
        Aria.download(this)
            .load(url) //读取下载地址
            .setFilePath(filePath) //设置文件保存的完整路径
            .create() //创建并启动下载
    }

    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        //在这里处理任务完成的状态
        showSuccess()
        doOpen(task.filePath)
    }

    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(this, filePath, null, null)
    }

    private fun choseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*") //设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, Constant.REQUEST_CODE_FILE)
    }

    private fun doUpload(it: StsTokenResp) {

        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName

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
                        showSuccess()
                        mDataFile.add(
                            FileInfoBean(
                                name = fileName,
                                url = objectKey,
                                ext = FileExtBean(size = File(filePath).length().toString())
                            )
                        )
                        rvTaskFile.visibility = View.VISIBLE
                        mAdapterFile.notifyDataSetChanged()

                    }

                }

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
                        mToast("文件上传失败")
                    }
                }

            })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_FILE) {
                val uri: Uri = data?.getData()!! //得到uri，后面就是将uri转化成file的过程。
                filePath = OssUtils().getPath(this, uri)
                fileName = filePath?.split("/")?.last()
                if (!filePath.isNullOrEmpty()) {
                    mViewModel.getSts()
                } else {
                    mToast(getString(R.string.errFile))
                }
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

        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

    }


}