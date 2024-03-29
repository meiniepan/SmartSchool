package com.xiaoneng.ss.module.school.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.circular.model.UnreadMemberResponse
import com.xiaoneng.ss.module.school.adapter.InvolveSimpleAdapter
import com.xiaoneng.ss.module.school.adapter.TaskLogAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task_detail.*
import java.io.File

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class TaskDetailActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var isOperator: Boolean = false
    var id: String = ""
    lateinit var mAdapterLog: TaskLogAdapter
    var mData: ArrayList<LogBean> = ArrayList()
    var beginTime: String = ""
    var endTime: String = ""
    var orderTime: String = ""
    var myLogBean: LogBean = LogBean()
    lateinit var mAdapterInvolve: InvolveSimpleAdapter
    lateinit var mAdapterPrincipal: InvolveSimpleAdapter
    var mDataInvolve = ArrayList<UserBeanSimple>()
    var mDataPrincipal = ArrayList<UserBeanSimple>()
    var taskBean: TaskDetailBean? = null
    private var type: String? = null
    lateinit var mAdapterFile: NoticeFileAdapter
    var mDataFile = ArrayList<FileInfoBean>()
    var mDataUnread = ArrayList<UnreadMemberBean>()
    var idString = ""
    var fileNum = 0
    var downloadNum = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(this).register()
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)!!
        type = intent.getStringExtra(Constant.TYPE)
        tvAddLogTaskDetail.setOnClickListener {
            mStartActivity<AddLogActivity>(this) {
                putExtra(Constant.DATA, myLogBean)
            }
        }
        tvConfirm.setOnClickListener {
            mAlert("是否关闭任务") {
                closeTask()
            }
        }
        tvFinishNum.setOnClickListener {
            if (mDataUnread.size > 0) {
                mStartActivity<UnreadMemberActivity>(this) {
                    var mData = UnreadMemberResponse(
                        data = mDataUnread,
                        id = taskBean?.id,
                        publishUserId = taskBean?.operatorid,
                        title = getString(R.string.taskTitle),
                        code = "1"
                    )
                    putExtra(Constant.DATA, mData)
                }
            }
        }
        initAdapter()
        initAdapterInvolve()
        initAdapterPrincipal()
    }

    private fun closeTask() {
        var bean = TaskBean()
        bean.id = taskBean?.id
        bean.token = UserInfo.getUserBean().token
        bean.status = "3"
        mViewModel.modifyTaskStatus(bean)
    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        if (type == "1") {
            mViewModel.getTaskInfo(id)
        } else if (type == "2") {
            mViewModel.getTaskInfo(id, "task")
        }
    }

    private fun initAdapter() {
        mAdapterLog = TaskLogAdapter(R.layout.item_task_log, mData, this)
        rvTaskDetail.apply {
            layoutManager = LinearLayoutManager(this@TaskDetailActivity)
            setAdapter(mAdapterLog)
        }

        mAdapterLog.setOnItemChildClickListener { adapter, view, position ->

            when (view.id) {

                R.id.tvAction0Log -> {
                    mStartActivity<AddLogActivity>(this) {
                        putExtra(Constant.DATA, mData[position])
                    }
                }
                R.id.tvAction1Log -> {
                    var bean = TaskLogRequest(
                        UserInfo.getUserBean().token,
                        mData[position].id ?: "",
                        examinestatus = "2"
                    )
                    mViewModel.refuseTask(bean)
                }
                R.id.tvAction2Log -> {
                    var bean = TaskLogRequest(
                        UserInfo.getUserBean().token,
                        mData[position].id ?: "",
                        examinestatus = "1"
                    )
                    mViewModel.refuseTask(bean)
                }
            }
        }
    }

    private fun initAdapterInvolve() {
        mAdapterInvolve = InvolveSimpleAdapter(R.layout.item_involve2, mDataInvolve)
        mAdapterInvolve.setMax(5)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapterInvolve
        }
        mAdapterInvolve.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterPrincipal() {

    }

    private fun initAdapterFile() {
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

    @SuppressLint("SetTextI18n")
    override fun initDataObserver() {
        mViewModel.mTaskDetailData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<TaskDetailBean>(it)?.let {
                    taskBean = it
                    tvTitleAddTask.text = it.taskname
                    beginTime = it.plantime

                    it.overtime?.let { endTime = it }
                    DateUtil.showTimeFromNet(beginTime, tvBeginDate, tvBeginTime)
                    DateUtil.showTimeFromNet(endTime, tvEndDate, tvEndTime)
                    if (it.remark.isNullOrEmpty()) {
                        tvRemark6.visibility = View.GONE
                    } else {
                        tvRemark6.visibility = View.VISIBLE
                        tvRemark6.text = it.remark
                    }
                    tvPublishName.text = "发布人:  " + it.operatorname
                    tvPublishTime.text = "发布时间:  " + it.createtime

                    //是否是发布人
                    isOperator = it.operatorid == UserInfo.getUserBean().uid
                    if (isOperator && type == "2" && taskBean?.status != "3") {
                        tvConfirm.visibility = View.VISIBLE
                    } else {
                        tvConfirm.visibility = View.GONE
                    }
                    mDataInvolve.clear()
                    for (i in 0 until it.involve.size) {
                        if (i < 5) {
                            mDataInvolve.add(it.involve[i])
                        }
                    }
                    mAdapterInvolve.notifyDataSetChanged()
                    //附件列表
                    mDataFile.clear()
                    var files = ArrayList<FileInfoBean>()
                    val resultType = object : TypeToken<ArrayList<FileInfoBean>>() {}.type
                    val gson = Gson()
                    try {
                        files = gson.fromJson<ArrayList<FileInfoBean>>(it.fileinfo, resultType)

                    } catch (e: Exception) {
//                        showError(getString(R.string.error_message))
                    }
                    mDataFile.addAll(files)
                    if (mDataFile.size > 0) {
                        llFile.visibility = View.VISIBLE
                        initAdapterFile()
                    } else {
                        llFile.visibility = View.GONE
                    }

                    //处理参与任务列表
                    mData.clear()
                    var logData: ArrayList<LogBean> = ArrayList()
                    logData.addAll(it.tasklist)
                    var isEmpty = true
                    if (type == "1") {
                        tvAddLogTaskDetail.visibility = View.VISIBLE
                    } else {
                        tvAddLogTaskDetail.visibility = View.GONE
                    }
                    var finishNum = 0
                    mDataUnread.clear()
                    logData.forEach {
                        if (it.completestatus == "1") {
                            finishNum++
                            isEmpty = false
                            mData.add(it)
                        } else {
                            mDataUnread.add(UnreadMemberBean(uid = it.uid, username = it.username))
                        }
                        if (it.uid == UserInfo.getUserBean().uid) {
                            myLogBean = it
                            if (it.feedback.isNullOrEmpty()) {
                                tvAddLogTaskDetail.visibility = View.VISIBLE
                            } else {
                                tvAddLogTaskDetail.visibility = View.GONE
                            }
                        }
                    }
                    tvFinishNum.text = "完成情况($finishNum/${logData.size})"
                    if (isEmpty) {
                        if (type == "1") {
                            rvTaskDetail.visibility = View.GONE
                            llLogEmpty.visibility = View.VISIBLE
                        } else if (type == "2") {
                            llLogEmpty.visibility = View.GONE
                            rvTaskDetail.visibility = View.VISIBLE
                            rvTaskDetail.notifyDataSetChanged()
                        }
                    } else {
                        llLogEmpty.visibility = View.GONE
                        mAdapterLog.setIsOperator(isOperator)
                        rvTaskDetail.visibility = View.VISIBLE
                        rvTaskDetail.notifyDataSetChanged()
                    }

                }
            }
        })

        mViewModel.mRefuseData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                getData()
//                netResponseFormat<TaskDetailBean>(it)?.let {
//
//                }
            }
        })

        mViewModel.mModifyTaskStatusData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
//                netResponseFormat<TaskDetailBean>(it)?.let {
//
//                }
            }
        })
    }

}