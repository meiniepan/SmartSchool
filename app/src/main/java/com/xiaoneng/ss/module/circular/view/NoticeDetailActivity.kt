package com.xiaoneng.ss.module.circular.view

import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jiang.awesomedownloader.downloader.AwesomeDownloader
import com.jiang.awesomedownloader.tool.PathSelector
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.isImage
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.circular.adapter.NoticeImgAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.model.FileInfoBean
import kotlinx.android.synthetic.main.activity_notice_detail.*
import java.io.File

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class NoticeDetailActivity : BaseLifeCycleActivity<CircularViewModel>() {
    var bean: NoticeBean? = null
    private var timer: CountDownTimer? = null
    var files = ArrayList<FileInfoBean>()
    lateinit var mAdapterImg: NoticeImgAdapter
    var mDataImg = ArrayList<FileInfoBean>()
    lateinit var mAdapterFile: NoticeFileAdapter
    var mDataFile = ArrayList<FileInfoBean>()
    var idString = ""
    var fileNum = 0
    var downloadNum = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_notice_detail
    }

    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)
        bean?.let {
            idString = "task_${it.id}_"
            tvRead.setOnClickListener {
                doRead()
            }
            tvNoticeTitle.text = it.title
            tvNoticeInfo.text = it.remark
            tvTime1.text = DateUtil.formatShowTime(it.noticetime!!)
            initReceivedUI()
            initFiles()
        }
    }

    private fun initFiles() {
        val resultType = object : TypeToken<ArrayList<FileInfoBean>>() {}.type
        val gson = Gson()
        try {
            files = gson.fromJson<ArrayList<FileInfoBean>>(bean?.fileinfo, resultType)
            if (files.size > 0) {
                files.forEach {
                    if (it.type?.isImage()!!) {
                        mDataImg.add(it)
                    } else {
                        mDataFile.add(it)
                    }
                }
            }
            if (mDataImg.size > 0) {
                rvNoticeImg.visibility = View.VISIBLE
                initAdapterImg()
            }
            if (mDataFile.size > 0) {
                fileNum = mDataFile.size
                showLoading()
                mDataFile.forEach {
                    var path = PathSelector(BaseApplication.instance).getDownloadsDirPath()
                    var name = idString + it.name
                    var filePath = path + File.separator + name
                    var filename = File(filePath)
                    if (filename.exists()) {
                        downloadNum++
                        if (downloadNum == fileNum) {
                            showSuccess()
                        }
                    } else {
                        doDown(it.url, name)
                    }
                }

                llNoticeFile.visibility = View.VISIBLE
                initAdapterFile()
            }
        } catch (e: Exception) {
//            showError(getString(R.string.error_message))
        }

    }

    private fun initAdapterImg() {
        mAdapterImg = NoticeImgAdapter(R.layout.item_notice_img, mDataImg)
        rvNoticeImg.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterImg)
        }

    }

    private fun initAdapterFile() {
        mAdapterFile = NoticeFileAdapter(R.layout.item_notice_file, mDataFile)
        rvNoticeFile.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapterFile)
        }
        mAdapterFile.setOnItemClickListener { _, view, position ->
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

    private fun initReceivedUI() {
        if (bean?.type == "feedback") {
            if (bean?.received == "1") {
                tvRead.visibility = View.GONE
                ivHasRead.visibility = View.VISIBLE
            } else {
                tvRead.visibility = View.VISIBLE
                ivHasRead.visibility = View.GONE
                preRead()
            }
        }
    }

    private fun doRead() {
        mViewModel.read(bean?.id!!, received = "1")
    }

    override fun initData() {
        super.initData()
        mViewModel.getNoticeDetail(bean?.id!!)
    }

    private fun preRead() {

        timer = object : CountDownTimer(0 * 1000, 1000) {
            override fun onFinish() {
                tvRead.text = "我已阅读"
                tvRead.isEnabled = true
                tvRead.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvRead.text = "我已阅读（${mm}s）"
                tvRead.isEnabled = false
                tvRead.setTextColor(resources.getColor(R.color.commonHint))
            }
        }.start()
    }

    private fun doDown(url: String?, fileName: String?) {
        AwesomeDownloader.init(BaseApplication.instance)
        //关闭通知栏
        AwesomeDownloader.option.showNotification = false
        val url = UserInfo.getUserBean().domain + url
        //获取应用外部照片储存路径
        val filePath = PathSelector(BaseApplication.instance).getDownloadsDirPath()
        //加入下载队列
        AwesomeDownloader.enqueue(url, filePath, fileName ?: "")
        AwesomeDownloader.setOnProgressChange { progress ->
            //do something...
        }.setOnStop { downloadBytes, totalBytes ->
            //do something...
        }.setOnFinished { filePath, fileName ->
            downloadNum++
            if (downloadNum == fileNum) {
                showSuccess()
            }
        }.setOnError { exception ->
            //do something...
        }
    }

    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(this, filePath, null, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun initDataObserver() {
        mViewModel.mNoticeDetail.observe(this, Observer { response ->
            response?.let {
                tvName.text = it.data.send_username
                tvReadNum.text = it.read + "/" + it.total
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                tvRead.visibility = View.GONE
                ivHasRead.visibility = View.VISIBLE
            }
        })
    }
}
