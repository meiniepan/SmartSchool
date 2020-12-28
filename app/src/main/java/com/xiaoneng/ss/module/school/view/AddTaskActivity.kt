package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.InvolveSimpleAdapter
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.TaskBean
import com.xiaoneng.ss.module.school.model.TaskDetailBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_task.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddTaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private val mMax: Int = 5
    var mId: String? = null
    var beginTime: String = DateUtil.getNearTimeBeginYear()
    var endTime: String = DateUtil.getNearTimeEndYear()
    var orderTime: String = ""
    lateinit var mAdapter: InvolveSimpleAdapter
    lateinit var mAdapterPrincipal: InvolveSimpleAdapter
    var mData = ArrayList<UserBeanSimple>()
    var mDataPrincipal = ArrayList<UserBeanSimple>()
    var taskBean = TaskBean()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var mDataClasses = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isDraftFirst: Boolean = true


    override fun getLayoutId(): Int = R.layout.activity_add_task


    override fun initView() {
        super.initView()
        mId = intent.getStringExtra(Constant.ID)
        initAdapter()
        initAdapterPrincipal()
        initUi()
        llBeginTime.apply {
            setOnClickListener {
                showDatePick(tvBeginDate, tvBeginTime) {
                    beginTime = this
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(tvEndDate, tvEndTime) {
                    endTime = this
                }
            }
        }
        ivTimingAddTask.apply {
            setOnClickListener {
                showDatePick(tvEndDate, tvEndTime) {
                    orderTime = this
                    setOrderPublish(this@apply)
                }
            }
        }


        tvAddParticipant.setOnClickListener {
            doAddParticipant()
        }
        tvAddPrincipal.setOnClickListener {
            doAddPrincipal()
        }
        tvConfirmAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun setOrderPublish(img: ImageView) {
        img.setImageResource(R.drawable.ic_timing_common)
        tvConfirmAddTask.text = "定时发布"
    }

    private fun initUi() {
        DateUtil.showTimeFromNet(beginTime, tvBeginDate, tvBeginTime)
        DateUtil.showTimeFromNet(endTime, tvEndDate, tvEndTime)
    }

    override fun initData() {
        super.initData()
        mId?.let {
            showLoading()
            mViewModel.getTaskInfo(it, "task")
        }
    }

    private fun addTask() {
        if (tvTitleAddTask.text.isEmpty() || receiveList.size == 0) {
            mToast(R.string.lack_info)
            return
        }
        var taskBean = TaskBean(
            UserInfo.getUserBean().token,
            taskname = tvTitleAddTask.text.toString(),
            plantime = beginTime,
            plantotal = 10.toString(),
            overtime = endTime,
            involve = Gson().toJson(receiveList),
            ordertime = orderTime,
            remark = etRemarkAddTask.text.toString(),
            status = "1"
        )
        mAlert("确定发布任务？") {
            if (mId.isNullOrEmpty()) {
                mViewModel.addTask(taskBean)
            } else {
                taskBean.id = mId
                mViewModel.modifyTaskStatus(taskBean)
            }
        }

    }

    private fun doAddPrincipal() {
//        mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_PRINCIPAL) {
//
//        }
    }

    private fun doAddParticipant() {
        mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_COURSE) {
            putExtra(Constant.DATA, mDataDepartment)
            putExtra(Constant.DATA2, mDataClasses)
            //从草稿箱第一次选择参与人，传入原有参与人数据
            if (isDraftFirst) {
                if (receiveList.size > 0) {
                    putExtra(Constant.DATA3, receiveList)
                }
            }
        }
    }


    private fun initAdapter() {
        mAdapter = InvolveSimpleAdapter(R.layout.item_involve2, mData)
        mAdapter.setMax(mMax)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterPrincipal() {

    }


    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        // 底部弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_save_draft, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvSaveDraft)
            .setOnClickListener { v: View? ->
                taskBean = TaskBean(
                    token = UserInfo.getUserBean().token,
                    taskname = tvTitleAddTask.text.toString(),
                    plantime = beginTime,
                    plantotal = 10.toString(),
                    overtime = endTime,
                    involve = Gson().toJson(receiveList),
                    ordertime = orderTime,
                    remark = etRemarkAddTask.text.toString(),
                    status = "0"
                )
                if (mId.isNullOrEmpty()) {
                    mViewModel.addTask(taskBean)
                } else {
                    taskBean.id = mId
                    mViewModel.modifyTaskStatus(taskBean)
                }
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvNoSaveDraft)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvCancelDraft)
            .setOnClickListener { v: View? ->

                bottomDialog.dismiss()
            }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_COURSE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                isDraftFirst = false
                mData.clear()
                receiveList.clear()
                mDataDepartment = data.getParcelableArrayListExtra(Constant.DATA)!!
                mDataClasses = data.getParcelableArrayListExtra(Constant.DATA2)!!
                mDataDepartment.forEach {
                    addDepartment(it)
                }
                mDataClasses.forEach {
                    addDepartment(it)
                }
                for (i in 0 until receiveList.size) {
                    if (i < mMax) {
                        mData.add(receiveList[i])
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        }

        if (requestCode == Constant.REQUEST_CODE_PRINCIPAL && resultCode == Activity.RESULT_OK) {

        }
    }

    private fun addDepartment(it: DepartmentBean) {
        if (it.num!!.toInt() > 0) {
            it.list.forEach {
                receiveList.add(
                    UserBeanSimple(
                        uid = it.uid,
                        realname = it.realname,
                        classid = it.classid,
                        usertype = it.usertype
                    )
                )
            }
        }
    }

    private fun doDustbin() {
        mAlert("删除后不可恢复请慎重选择是否删除该任务", "是否确定删除该任务") {

            mViewModel.delTaskDraft(mId ?: "")
        }
    }

    override fun initDataObserver() {
        mViewModel.mAddTaskData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mModifyTaskStatusData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mTaskDetailData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<TaskDetailBean>(it)?.let {
                    ivDustbin.visibility = View.VISIBLE
                    ivDustbin.setOnClickListener {
                        doDustbin()
                    }
                    it.plantime?.let {
                        beginTime = it
                    }
                    it.overtime?.let {
                        endTime = it
                    }
                    it.involve?.let {
                        receiveList.clear()
                        mData.clear()
                        receiveList.addAll(it)
                        for (i in 0 until receiveList.size) {
                            if (i < 4) {
                                mData.add(receiveList[i])
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                    initUi()
                    it.taskname?.let {
                        tvTitleAddTask.setText(it)
                    }
                    it.remark?.let {
                        etRemarkAddTask.setText(it)
                    }
                    it.ordertime?.let {
                        if (it.isNotEmpty()) {
                            setOrderPublish(ivTimingAddTask)
                        }
                    }
                }
            }
        })

    }

}