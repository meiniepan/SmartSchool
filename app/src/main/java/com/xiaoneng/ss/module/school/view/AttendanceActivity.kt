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
import com.xiaoneng.ss.module.school.adapter.*
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_attendance.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AttendanceActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterSchool: AttendanceSchoolAdapter
    lateinit var mAdapterMaster: AttendanceMasterAdapter
    lateinit var mAdapterTeacher: AttendanceTeacherAdapter
    lateinit var mAdapterStudent: AttendanceStuAdapter
    var mSchoolData: ArrayList<AttendanceSchoolBean> = ArrayList()
    var mMasterData: ArrayList<AttendanceBean> = ArrayList()
    var mTeacherData: ArrayList<AttCourseBean> = ArrayList()
    var mStudentData: ArrayList<AttendanceStuBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()
    private val bottomDialog: Dialog by lazy {
        initDialog()
    }
    var titles = ArrayList<String>()

    override fun getLayoutId(): Int = R.layout.activity_attendance


    override fun initView() {
        super.initView()
        tvTimeToday.text = "今天是" + DateUtil.formatTitleToday()
        initTitle()
        initAdapter()

    }

    private fun initAdapter() {
        if (UserInfo.getUserBean().usertype == "1") {
            titles.add("个人考勤")
            if (UserInfo.getUserBean().isad == "1") {
                titles.add("课堂考勤")
                initAdapterTeacher()
            } else {
                initAdapterStu()
                initStudentApplyLeave()
            }

        } else if (UserInfo.getUserBean().usertype == "2") {

            titles.add("课堂考勤")
            if (UserInfo.getUserBean().classmaster == "1") {
                titles.add("班级考勤")
                initAdapterMaster()
                llSearch.visibility = View.VISIBLE
            } else {
                initAdapterTeacher()
                tvLabel2Attendance.visibility = View.GONE
                tvLabel3Attendance.visibility = View.GONE
            }
        } else if (UserInfo.getUserBean().usertype == "99") {
            titles.add("校级考勤")
            ivAddAttendance.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    mStartActivity<AddStudentActivity>(this@AttendanceActivity)
                }
            }
            tvLabel2Attendance.visibility = View.GONE
            tvLabel3Attendance.visibility = View.GONE
            initAdapterSchool()
        } else {

        }
        tvLabel1Attendance.text = titles[0]

    }

    override fun onResume() {
        super.onResume()
        getData()
    }


    override fun getData() {
        rvAttendance.showLoadingView()
        if (UserInfo.getUserBean().usertype == "1") {
            if (UserInfo.getUserBean().isad == "1") {
                mViewModel.getAttTimetable(time = Constant.TO_DO)
            } else {
                mViewModel.getAttendanceStu(time = Constant.TO_DO)
            }

        } else if (UserInfo.getUserBean().usertype == "2") {
            if (UserInfo.getUserBean().classmaster == "1") {
                mViewModel.getAttendanceTea(time = Constant.TO_DO)
            } else {
                mViewModel.getAttTimetable(time = Constant.TO_DO)
            }
        } else if (UserInfo.getUserBean().usertype == "99") {
            mViewModel.getAttendanceSchool(time = Constant.TO_DO)
        } else {
            mViewModel.getAttendanceStu()
        }
    }

    private fun initStudentApplyLeave() {
        tvApplyLeave.apply {
            visibility = if (UserInfo.getUserBean().usertype == "1") {
                View.VISIBLE
            } else {
                View.GONE
            }
            setOnClickListener {
                mStartActivity<ApplyLeaveActivity>(this@AttendanceActivity)
            }
        }
    }

    private fun initTitle() {


        tvLabel1Attendance.setOnClickListener {
            bottomDialog.show()
        }
        tvLabel3Attendance.apply {
            text = chosenDay
            setOnClickListener {
                showDateDayPick(this) {
                    chosenDay = this
                }
            }
        }


    }


    private fun initAdapterSchool() {
        mAdapterSchool = AttendanceSchoolAdapter(R.layout.item_attendance_school, mSchoolData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterSchool)
        }
        mAdapterSchool.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterMaster() {
        mAdapterMaster = AttendanceMasterAdapter(R.layout.item_attendance_master, mMasterData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterMaster)
        }
        mAdapterMaster.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tvLeaveType)
                mStartActivity<AddClassAttendanceTypeMasterActivity>(this) {
                    putExtra(Constant.DATA, mMasterData[position])
                }
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
            mStartActivity<Attendance2CourseActivity>(this) {
                putExtra(Constant.ID, mTeacherData[position].id)
            }
        }
    }

    private fun initDialog(): Dialog {

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
            layoutManager = LinearLayoutManager(this@AttendanceActivity)
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
            if (titles[position] == "班级考勤") {
                llSearch.visibility = View.VISIBLE
            } else if (titles[position] == "课堂考勤") {
                llSearch.visibility = View.GONE
            }
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mAttendanceTeaData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttendanceResponse>(it)?.let {
                    mMasterData.clear()
                    mMasterData.addAll(it.data)
                    rvAttendance.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mAttendanceStuData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttendanceResponse>(it)?.let {

                    mStudentData.clear()
                    mStudentData.addAll(it.attendances)
                    rvAttendance.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mAttTimetableData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttCourseResponse>(it)?.let {
                    mTeacherData.clear()
                    mTeacherData.addAll(it.list)
                    rvAttendance.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mAttendanceSchoolData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttendanceSchoolBean>(it)?.let {
                    mSchoolData.clear()

                    repeat(5) { o ->
                        mSchoolData.add(it)
                    }

                    rvAttendance.notifyDataSetChanged()
                }
            }
        })

    }
}
