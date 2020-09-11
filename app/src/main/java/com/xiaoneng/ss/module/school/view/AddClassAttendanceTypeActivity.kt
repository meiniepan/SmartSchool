package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
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
            tvAddClassAttendance1.show(true)
        }
        if (bean.has_thingleave == "1") {
            tvAddClassAttendance2.show(true)
        }
        if (bean.has_courselate == "1") {
            tvAddClassAttendance3.show(true)
        }
        if (bean.has_truant == "1") {
            tvAddClassAttendance4.show(true)
        }
        if (bean.has_morninglate == "1") {
            doMorningLate()

        }

        llAddClassAttendance1.setOnClickListener {
            mStartActivity<LeaveTypeActivity>(this) {

            }
        }
        llAddClassAttendance2.setOnClickListener {
        }
        llAddClassAttendance3.setOnClickListener {
        }
        llAddClassAttendance4.setOnClickListener {
        }

    }

    private fun doMorningLate() {
        tvAddClassAttendance5.show(true)
        llAddClassAttendance5.setOnClickListener {
            mAlert(
                "点击该项，早间迟到将会直接被删除",
                "是否删除早间迟到"
            ) {
                for (i in bean.attlists!!){
                    if (i.type == "1"){
                        mViewModel.deleteAttendance(i.id?:"")
                    }
                }
            }
        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
        mViewModel.mDeleteAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

    }


}