package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mToast
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
class AddClassAttendanceTypeMasterActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var bean: AttendanceBean

    override fun getLayoutId(): Int = R.layout.activity_add_class_attendance_type


    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)!!
        if (bean.has_sickleave != "0") {
            tvAddClassAttendance1.show(true)
        }
        if (bean.has_thingleave != "0") {
            tvAddClassAttendance2.show(true)
        }
        if (bean.has_courselate != "0") {
            tvAddClassAttendance3.show(true)
        }
        if (bean.has_truant != "0") {
            tvAddClassAttendance4.show(true)
        }
        if (bean.has_morninglate != "0") {
            doMorningLate()

        }

        llAddClassAttendance1.setOnClickListener {
            mStartActivity<LeaveTypeActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "2")
                putExtra(Constant.DATA, bean)
            }
        }
        llAddClassAttendance2.setOnClickListener {
            mStartActivity<LeaveTypeActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "1")
                putExtra(Constant.DATA, bean)
            }
        }
        llAddClassAttendance3.setOnClickListener {
            mStartActivity<ChooseCourseToLeaveActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "课堂考勤迟到")//状态0迟到1已完成2旷课3请假
                putExtra(Constant.DATA, bean)
            }
        }
        llAddClassAttendance4.setOnClickListener {
            mStartActivity<ChooseCourseToLeaveActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "旷课")
                putExtra(Constant.DATA, bean)
            }
        }

    }

    private fun doMorningLate() {
        tvAddClassAttendance5.show(true)
        llAddClassAttendance5.setOnClickListener {
            mAlert(
                "点击该项，早间迟到将会直接被删除",
                "是否删除早间迟到"
            ) {
                for (i in bean.attlists!!) {
                    if (i.type == "1") {
                        mViewModel.deleteAttendance(i.id ?: "")
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
                mToast(R.string.deal_done)
                mStartActivity<AttendanceActivity>(this)
            }
        })

    }


}