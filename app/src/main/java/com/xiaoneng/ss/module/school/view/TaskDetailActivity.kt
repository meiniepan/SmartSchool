package com.xiaoneng.ss.module.school.view

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.InvolveSimpleAdapter
import com.xiaoneng.ss.module.school.adapter.TaskLogAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task_detail.*
import org.jetbrains.anko.toast

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
    lateinit var taskBean: TaskDetailBean
    private var type: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
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
        initAdapter()
        initAdapterInvolve()
        initAdapterPrincipal()
    }

    private fun closeTask() {
        var bean = TaskBean()
        bean.id = taskBean.id
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
        mAdapterLog = TaskLogAdapter(R.layout.item_task_log, mData)
        rvTaskDetail.apply {
            layoutManager = LinearLayoutManager(this@TaskDetailActivity)
            adapter = mAdapterLog
        }

        mAdapterLog.setOnItemChildClickListener { adapter, view, position ->

            when (view.id) {

                R.id.tvAction0Log -> {
                    mStartActivity<AddLogActivity>(this) {
                        putExtra(Constant.DATA, myLogBean)
                    }
                }
                R.id.tvAction1Log -> {
                    var bean = TaskLogRequest(
                        UserInfo.getUserBean().token,
                        myLogBean.taskid ?: "",
                        examinestatus = "2"
                    )
                    mViewModel.refuseTask(bean)
                }
                R.id.tvAction2Log -> {
                    var bean = TaskLogRequest(
                        UserInfo.getUserBean().token,
                        myLogBean.taskid ?: "",
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
                    tvRemark6.text = it.remark
                    tvPublishName.text = "发布人:  " + it.operatorname
                    tvPublishTime.text = "发布时间:  " + it.createtime

                    //是否是发布人
                    isOperator = it.operatorid == UserInfo.getUserBean().uid
                    if (isOperator) {
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

                    //处理参与任务列表
                    mData.clear()
                    var logData: ArrayList<LogBean> = ArrayList()
                    logData.addAll(it.tasklist)
                    var isEmpty = true
                    logData.forEach {
                        if (!it.feedback.isNullOrEmpty()) {
                            isEmpty = false
                            mData.add(it)
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
                    if (isEmpty) {
                        llLogEmpty.visibility = View.VISIBLE
                    } else {
                        llLogEmpty.visibility = View.GONE
                        mAdapterLog.setIsOperator(isOperator)
                        mAdapterLog.notifyDataSetChanged()
                    }

                }
            }
        })

        mViewModel.mRefuseData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                getData()
//                netResponseFormat<TaskDetailBean>(it)?.let {
//
//                }
            }
        })

        mViewModel.mModifyTaskStatusData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
//                netResponseFormat<TaskDetailBean>(it)?.let {
//
//                }
            }
        })
    }

}