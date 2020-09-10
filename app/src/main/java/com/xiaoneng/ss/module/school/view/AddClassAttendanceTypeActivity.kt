package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.school.model.AttendanceBean
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
class AddClassAttendanceTypeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var bean: AttendanceBean

    override fun getLayoutId(): Int = R.layout.activity_add_class_attendance_type


    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)
        if (bean.has_sickleave == "1") {

        } else {
        }

        llAddClassAttendance1.setOnClickListener {
//            mViewModel.addAttendanceByMaster()
        }
        llAddClassAttendance2.setOnClickListener {
        }
        llAddClassAttendance3.setOnClickListener {
        }
        llAddClassAttendance4.setOnClickListener {
        }
        llAddClassAttendance5.setOnClickListener {
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