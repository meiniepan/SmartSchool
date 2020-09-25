package com.xiaoneng.ss.module.school.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.AttendanceMasterAdapter
import com.xiaoneng.ss.module.school.model.AttCourseBean
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.model.AttendanceResponse
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_attendance.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class Attendance2CourseActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterMaster: AttendanceMasterAdapter
    var mMasterData: ArrayList<AttendanceBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()
    var bean:AttCourseBean? = null


    override fun getLayoutId(): Int = R.layout.activity_attendance


    override fun initView() {
        super.initView()
        llToday.visibility = View.GONE
        llSearch.visibility = View.VISIBLE
        bean = intent.getParcelableExtra(Constant.DATA)
        initTitle()
        initAdapter()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    override fun getData() {
        super.getData()
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
//        mMasterData.add(AttendanceBean(realname = "hsjdf"))
        rvAttendance.showLoadingView()
        if (UserInfo.getUserBean().usertype == "1") {
            if (UserInfo.getUserBean().isad == "1") {
                bean?.classid?.let {
                    mViewModel.getAttendanceByStuAdmin(time = "", classid = it)
                }
            }

        } else if (UserInfo.getUserBean().usertype == "2"||UserInfo.getUserBean().usertype == "99") {

            bean?.classid?.let {

                mViewModel.getAttendanceTea(time = "", classid = it)
            }
        }
    }

    private fun initAdapter() {
        initAdapterMaster()

    }


    private fun initTitle() {
        llToday.visibility = View.GONE
        llSpinner.visibility = View.GONE
    }


    private fun initAdapterMaster() {
        mAdapterMaster = AttendanceMasterAdapter(R.layout.item_attendance_master, mMasterData)
        rvAttendance.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterMaster)
        }
        mAdapterMaster.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tvLeaveType)
                mStartActivity<AddClassAttendanceTypeTeacherActivity>(this) {
                    putExtra(Constant.DATA, mMasterData[position])
                    putExtra(Constant.DATA2, bean)
                }
        }
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
                    mMasterData.clear()
                    mMasterData.addAll(it.data)
                    rvAttendance.notifyDataSetChanged()
                }
            }
        })

    }
}
