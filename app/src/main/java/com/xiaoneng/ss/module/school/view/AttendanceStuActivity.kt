package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.AttendanceMasterAdapter
import com.xiaoneng.ss.module.school.adapter.AttendanceStuAdapter
import com.xiaoneng.ss.module.school.adapter.AttendanceTeacherAdapter
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.model.AttendanceStuBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_attendance_stu.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AttendanceStuActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterMaster: AttendanceMasterAdapter
    lateinit var mAdapterTeacher: AttendanceTeacherAdapter
    lateinit var mAdapterStudent: AttendanceStuAdapter
    var mMasterData: ArrayList<AttendanceBean> = ArrayList()
    var mTeacherData: ArrayList<AttendanceBean> = ArrayList()
    var mStudentData: ArrayList<AttendanceStuBean> = ArrayList()
    private val bottomDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_attendance_stu


    override fun initView() {
        super.initView()
        initTitle()
        initAdapter()
        tvApplyLeave.setOnClickListener {
            mStartActivity<ApplyLeaveActivity>(this)
        }
    }

    private fun initAdapter() {
        if (UserInfo.getUserBean().usertype == "1") {
            initAdapterStu()
        } else if (UserInfo.getUserBean().usertype == "2") {
            if (UserInfo.getUserBean().classmaster == "1") {
                initAdapterMaster()
            } else {
                initAdapterTeacher()
            }
        } else if (UserInfo.getUserBean().usertype == "99") {
            initAdapterMaster()
        } else {
            initAdapterStu()
        }

    }

    private fun initTitle() {

        tvLabel1Attendance.text = "校级考勤"
        tvLabel1Attendance.setOnClickListener {
            bottomDialog.show()
        }
        tvLabel3Attendance.apply {
            text = DateUtil.formatDateCustomMmDay()
            setOnClickListener {

                showDateDayPick(this) {}
            }
        }


    }


    override fun initData() {
        super.initData()
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
        if (UserInfo.getUserBean().usertype == "1") {
            mViewModel.getAttendanceStu(atttime = "20200814")
        } else if (UserInfo.getUserBean().usertype == "2") {
            if (UserInfo.getUserBean().classmaster == "1") {

            } else {

            }
        } else if (UserInfo.getUserBean().usertype == "99") {

        } else {
            mViewModel.getAttendanceStu()
        }
    }

    private fun initAdapterMaster() {
        mAdapterMaster = AttendanceMasterAdapter(R.layout.item_attendance_master, mMasterData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterMaster)
        }
        mAdapterMaster.setOnItemClickListener { _, view, position ->
            mStartActivity<AddStudentActivity>(this)
        }
    }

    private fun initAdapterStu() {
        mAdapterStudent = AttendanceStuAdapter(R.layout.item_attendance_stu, mStudentData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterStudent)
        }
        mAdapterStudent.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterTeacher() {
        mAdapterTeacher = AttendanceTeacherAdapter(R.layout.item_attendance_teacher, mTeacherData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterTeacher)
        }
        mAdapterTeacher.setOnItemClickListener { _, view, position ->
            mStartActivity<AddStudentActivity>(this)
        }
    }

    private fun initDialog(): Dialog {
        var titles = ArrayList<String>().apply {
            add("校级考勤")
            add("班级考勤")
            add("课堂考勤")
        }
        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AttendanceStuActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvLabel1Attendance.text = titles[position]
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mAttendanceMasterData.observe(this, Observer { response ->
            response?.let {
                mMasterData.clear()
                mMasterData.addAll(it.data)
                if (mMasterData.size > 0) {
                    mAdapterMaster.notifyDataSetChanged()
                } else {
                    showEmpty()
                }
            }
        })

        mViewModel.mAttendanceStuData.observe(this, Observer { response ->
            response?.let {
                mStudentData.clear()
                mStudentData.addAll(it.attendances)
                rvAttendance.recyclerView.notifyDataSetChanged()
            }
        })

    }
}
