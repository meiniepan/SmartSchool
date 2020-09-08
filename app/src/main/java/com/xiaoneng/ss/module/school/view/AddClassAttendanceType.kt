package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_class_attendance_type.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddClassAttendanceType : BaseLifeCycleActivity<SchoolViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_add_class_attendance_type


    override fun initView() {
        super.initView()
        llAddClassAttendance1.setOnClickListener {
//            mViewModel.addAttendanceByMaster()
        }
        llAddClassAttendance2.setOnClickListener {
        }
        llAddClassAttendance3.setOnClickListener {
        }

    }



    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

    }


}