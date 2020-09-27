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
    private var leaveStr = ""
    lateinit var mAdapter: ChooseCourseAdapter
    var mData: ArrayList<AttCourseBean> = ArrayList()
    var mDataChosen: ArrayList<AttCourseBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()
    var chosenDayNet = DateUtil.formatDateCustomDay()
    var leaveType :String?= ""
    var delNum = 0
    var resNum = 0
    var addNum = 0
    var resAddNum = 0
    lateinit var bean: AttendanceBean

    override fun getLayoutId(): Int = R.layout.activity_choose_course_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text = "今天是" + DateUtil.formatTitleToday()
        bean = intent.getParcelableExtra(Constant.DATA)
        leaveType = intent.getStringExtra(Constant.LEAVE_TYPE)
        bean?.let {
//            initUI()
        }
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        tvChooseDay.apply {
            bean.mDate?.let {
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
        when {
            leaveType.isNullOrEmpty() -> {

                setResult(
                    Activity.RESULT_OK,
                    intent.putParcelableArrayListExtra(Constant.DATA, mDataChosen)
                        .putExtra(Constant.TITLE, DateUtil.formatTitleToday(chosenDayNet))
                        .putExtra(Constant.TITLE_2, chosenDayNet)
                )
                finish()
            }
            leaveType == "0" -> {
                leaveStr = "课堂考勤迟到"
                leaveType?.let {
                    applyLeave(it)
                }

            }
            leaveType == "2" -> {
                leaveStr = "旷课"
                leaveType?.let {
                    applyLeave(it)
                }
            }
        }
    }

    private fun applyLeave(leaveType: String) {
        delNum = 0
        resNum = 0
        var uId = bean.uid ?: ""
        var uType = "1"
        if (mDataChosen.size > 0) {
            var has = false
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
            if (delNum == 0) {
                doApplyLeave()
            }
        }
    }

    private fun doApplyLeave() {
        addNum = mDataChosen.size
        resAddNum = 0
        var msg = bean.cno + bean.realname + "\n" + bean.levelname + bean.classname
        mAlert(msg, "请确认学生身份") {
            mDataChosen.forEach {
                mViewModel.addAttendance(
                    LeaveBean(
                        UserInfo.getUserBean().token,
                        type = "3",
                        status = leaveType?:"",
                        leavetype = "0",
                        uid = bean.uid!!,
                        atttime = chosenDay,
                        crsid = it.id ?: "",
                        teacheruid = it.teacheruid ?: "",
                        usertype = "1",
                        remark = ""
                    )
                )
            }
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getAttTimetable(time = chosenDayNet, uId = bean.uid ?: "")
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

    override fun initDataObserver() {
        mViewModel.mAttTimetableData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttCourseResponse>(it)?.let {
                    mData.clear()
                    mData.addAll(it.list)
                    mData.forEach { bean ->
                        bean.attlists?.let {
                            if (it.size > 0) {
                                bean.checked = true
                            }
                        }
                    }
                    rvChooseCourse.notifyDataSetChanged()
                }

            }
        })

        mViewModel.mDeleteAttendanceData.observe(this, Observer { response ->
            response?.let {
                resNum++
                if (resNum == delNum) {
                    doApplyLeave()
                }
            }
        })

        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                resAddNum++
                if (resAddNum == addNum) {
                    toast(R.string.deal_done)
                    finish()
                }
            }
        })

    }


}