package com.xiaoneng.ss.module.school.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.ChooseCourseAdapter
import com.xiaoneng.ss.module.school.model.AttCourseBean
import com.xiaoneng.ss.module.school.model.AttCourseResponse
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.model.LeaveBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_choose_course_leave.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ChooseCourseToLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var checkNum: Int = 0
    private var isDelete: Boolean = false
    lateinit var mAdapter: ChooseCourseAdapter
    var mData: ArrayList<AttCourseBean> = ArrayList()
    var mDataChosen: ArrayList<AttCourseBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()
    var chosenDayNet = DateUtil.formatDateCustomDay()
    var leaveType: String? = ""
    var leaveStr: String? = ""
    var delNum = 0
    var resNum = 0
    var bean: AttendanceBean? = null

    override fun getLayoutId(): Int = R.layout.activity_choose_course_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text = "今天是" + DateUtil.formatTitleToday()
        bean = intent.getParcelableExtra(Constant.DATA)
        leaveStr = intent.getStringExtra(Constant.LEAVE_TYPE)
        bean?.let {
//            initUI()
        }
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        tvChooseDay.apply {
            bean?.mDate?.let {
                if (it.isEmpty()) {
                    text = chosenDay
                } else {
                    text = DateUtil.formatDateCustomMmDay(it)
                    chosenDayNet = it
                }
            }

            setOnClickListener {
                showDateDayPick(this) {
                    chosenDayNet = this
                    getData()
                }
            }

        }

        initAdapter()

    }

    private fun doConfirm() {
        mDataChosen.clear()
        mData.forEach {
            if (it.checked) {
                mDataChosen.add(it)
            }
        }
        when (leaveStr) {
            "课堂考勤迟到" -> {
                leaveType = "0"
                applyLeave()

            }
            "旷课" -> {
                leaveType = "2"
                applyLeave()
            }

            else -> {
                if (!leaveStr.isNullOrEmpty()) {
                    delNum = 0
                    resNum = 0
                    if (mDataChosen.size > 0) {
                        doSetResult()
                    } else {
                        if (checkNum > 0) {
                            deleteOnly()
                        } else {
                            finish()
                        }
                    }
                } else {
                    doSetResult()
                }
            }
        }
    }

    private fun doSetResult() {
        setResult(
            Activity.RESULT_OK,
            intent.putParcelableArrayListExtra(Constant.DATA, mDataChosen)
                .putExtra(Constant.TITLE, chosenDayNet)
        )
        finish()
    }

    private fun applyLeave() {
        delNum = 0
        resNum = 0
        var uId = bean?.uid ?: ""
        var uType = "1"
        if (mDataChosen.size > 0) {
            var has = false
            bean?.attlists?.let {
                it.forEach {
                    if (it.attendances == leaveType) {
                        delNum++
                    }
                }
                it.forEach {
                    if (it.attendances == leaveType) {
                        mViewModel.deleteAttendance(it.id ?: "")
                    }
                }
            }
            if (delNum == 0) {
                doApplyLeave()
            }
        } else {
            if (checkNum > 0) {
                deleteOnly()
            } else {
                finish()
            }
        }
    }

    private fun deleteOnly() {
        mAlert(
            "请确认请假时间是否正确\n" +
                    "没有选择时间的请假记录将会被删除",
            "本条请假信息将会被删除"
        ) {
            isDelete = true
            bean?.attlists?.let {
                it.forEach {
                    if (it.attendances == leaveStr) {
                        delNum++
                    }
                }
                it.forEach {
                    if (it.attendances == leaveStr) {
                        mViewModel.deleteAttendance(it.id ?: "")
                    }
                }
            }
        }
    }

    private fun doApplyLeave() {
        var msg = bean?.cno + bean?.realname + "\n" + bean?.levelname + bean?.classname
        mAlert(msg, "请确认学生身份") {
            var courseList = ""
            mDataChosen.forEach {
                courseList += it.id + ","
            }
            if (courseList.isNotEmpty()) {
                courseList.substring(0, courseList.length - 1)
            }
            mViewModel.addAttendance(
                LeaveBean(
                    UserInfo.getUserBean().token,
                    type = "3",
                    status = leaveType ?: "",
                    leavetype = "0",
                    uid = bean?.uid!!,
                    atttime = chosenDayNet,
                    crsid = courseList,
                    usertype = "1",
                    remark = ""
                )
            )
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        rvChooseCourse.showLoadingView()
        mViewModel.getAttTimetable(time = chosenDayNet, uId = bean?.uid ?: "")
    }

    private fun initAdapter() {
        mAdapter = ChooseCourseAdapter(R.layout.item_choose_course, mData)
        rvChooseCourse.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mData[position].checked = !mData[position].checked
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun checkCourse(bean: AttCourseBean) {
        bean?.attlists?.forEach {
            if (it.attendances == leaveStr) {
                checkNum++
                bean?.checked = true
                return
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mAttTimetableData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttCourseResponse>(it)?.let {
                    mData.clear()
                    mData.addAll(it.list)
                    mData.forEach { bean ->
                        checkCourse(bean)
                    }
                    rvChooseCourse.notifyDataSetChanged()
                }

            }
        })

        mViewModel.mDeleteAttendanceData.observe(this, Observer { response ->
            response?.let {
                resNum++
                if (resNum == delNum) {
                    if (isDelete) {
                        toast(R.string.deal_done)
                        mStartActivity<AttendanceActivity>(this)
                    } else {
                        doApplyLeave()
                    }
                }
            }
        })

        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                mStartActivity<AttendanceActivity>(this)
            }
        })

    }


}