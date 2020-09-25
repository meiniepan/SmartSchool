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
import com.xiaoneng.ss.model.ClassBean
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
    var mAdapterTeacher: AttendanceTeacherAdapter? = null
    lateinit var mAdapterStudent: AttendanceStuAdapter
    var mSchoolData: ArrayList<AttendanceSchoolBean> = ArrayList()
    var mMasterData: ArrayList<AttendanceBean> = ArrayList()
    var mTeacherData: ArrayList<AttCourseBean> = ArrayList()
    var mStudentData: ArrayList<AttendanceStuBean> = ArrayList()
    var mClassData: ArrayList<ClassBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomDay()
    private val bottomDialog1: Dialog by lazy {
        initDialog1()
    }
    private val bottomDialog2: Dialog by lazy {
        initDialog2()
    }
    var titles1 = ArrayList<String>()
    var titles2 = ArrayList<String>()

    override fun getLayoutId(): Int = R.layout.activity_attendance


    override fun initView() {
        super.initView()
        tvTimeToday.text = "今天是" + DateUtil.formatTitleToday()
        initTitle()
        initAdapter()

    }

    private fun initAdapter() {
        if (UserInfo.getUserBean().usertype == "1") {
            llSearch.visibility = View.GONE
            tvLabel2Attendance.visibility = View.GONE
            tvLabel3Attendance.visibility = View.GONE
            if (UserInfo.getUserBean().isad == "1") {
                titles1.add("课堂考勤")
                initAdapterTeacher()
                mAdapterTeacher?.setIsTeacher(false)
            } else {
                initAdapterStu()
                initStudentApplyLeave(true)
            }
            titles1.add("个人考勤")
//todo 测试
        } else if (UserInfo.getUserBean().usertype == "2" || UserInfo.getUserBean().usertype == "99") {

            if (UserInfo.getUserBean().classmaster == "1") {
                titles1.add("班级考勤")
                initAdapterMaster()
                llSearch.visibility = View.VISIBLE
            } else {
                initAdapterTeacher()
                mAdapterTeacher?.setIsTeacher(true)
                tvLabel2Attendance.visibility = View.GONE
                tvLabel3Attendance.visibility = View.GONE
            }
            titles1.add("课堂考勤")
        } else if (UserInfo.getUserBean().usertype == "99") {
            titles1.add("校级考勤")
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
        tvLabel1Attendance.text = titles1[0]

    }

    override fun initData() {
        super.initData()
        getData()
    }


    override fun getData() {
        rvAttendance.showLoadingView()
        if (UserInfo.getUserBean().usertype == "1") {
            if (UserInfo.getUserBean().isad == "1") {
                getTimetable()
            } else {
                getStuData()
            }
//todo 测试
        } else if (UserInfo.getUserBean().usertype == "2" || UserInfo.getUserBean().usertype == "99") {
            if (UserInfo.getUserBean().classmaster == "1") {
                getAttendanceMaster()
            } else {
                getTimetable()
            }
        } else if (UserInfo.getUserBean().usertype == "99") {
            mViewModel.getAttendanceSchool()
        } else {
            mViewModel.getAttendanceStu()
        }
    }

    private fun getStuData() {
        mViewModel.getAttendanceStu(time = "")
    }

    private fun getAttendanceMaster() {
        mViewModel.getAttendanceTea(time = chosenDay)
    }

    private fun getTimetable() {
        mViewModel.getAttTimetable(time = chosenDay)
    }

    private fun initStudentApplyLeave(b: Boolean) {
        tvApplyLeave.apply {
            visibility = if (UserInfo.getUserBean().usertype == "1" && b) {
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
            bottomDialog1.show()
        }

        tvLabel2Attendance.setOnClickListener {
            bottomDialog2.show()
        }
        tvLabel3Attendance.apply {
            text = DateUtil.formatDateCustomMmDay()
            setOnClickListener {
                showDateDayPick(this) {
                    chosenDay = this
                    rvAttendance.showLoadingView()
                    getAttendanceMaster()
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
        mAdapterTeacher?.setOnItemClickListener { _, view, position ->
            mStartActivity<Attendance2CourseActivity>(this) {
                putExtra(Constant.DATA, mTeacherData[position])
            }
        }
    }

    private fun initDialog1(): Dialog {

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
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles1)
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
            tvLabel1Attendance.text = titles1[position]
            if (titles1[position] == "班级考勤") {
                rvAttendance.showLoadingView()
                llSearch.visibility = View.VISIBLE
                tvLabel2Attendance.visibility = View.VISIBLE
                tvLabel3Attendance.visibility = View.VISIBLE
                initAdapterMaster()
                getAttendanceMaster()
            } else if (titles1[position] == "课堂考勤") {
                llSearch.visibility = View.GONE
                rvAttendance.showLoadingView()
                tvLabel2Attendance.visibility = View.GONE
                tvLabel3Attendance.visibility = View.GONE
                initAdapterTeacher()
                if (UserInfo.getUserBean().usertype == "1") {
                    mAdapterTeacher?.setIsTeacher(false)
                } else {
                    mAdapterTeacher?.setIsTeacher(true)
                }
                initStudentApplyLeave(false)
                getTimetable()
            } else if (titles1[position] == "个人考勤") {
                llSearch.visibility = View.GONE
                rvAttendance.showLoadingView()
                tvLabel2Attendance.visibility = View.GONE
                tvLabel3Attendance.visibility = View.GONE
                initAdapterStu()
                getStuData()
                initStudentApplyLeave(true)
            }
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    private fun initDialog2(): Dialog {

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
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles2)
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
            tvLabel2Attendance.text = titles2[position]
            rvAttendance.showLoadingView()
            mViewModel.getAttendanceTea(
                time = Constant.TO_DO,
                classid = mClassData[position].classid
            )
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mAttendanceTeaData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttendanceResponse>(it)?.let {
                    if (it.data.size > 0) {
                        mMasterData.clear()
                        mMasterData.addAll(it.data)
                        rvAttendance.notifyDataSetChanged()
                    }
                    if (it.classs.size > 0) {
                        mClassData.clear()
                        titles2.clear()
                        mClassData.addAll(it.classs)
                        mClassData.forEach {
                            titles2.add(it.classname)
                            if (it.choice == "1") {
                                tvLabel2Attendance.text = it.classname
                            }
                        }
                    }
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
