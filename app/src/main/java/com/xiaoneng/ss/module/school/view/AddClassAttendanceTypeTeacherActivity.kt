package com.xiaoneng.ss.module.school.view

import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.model.LeaveBean
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
class AddClassAttendanceTypeTeacherActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var bean: AttendanceBean
    var chosenDay = DateUtil.formatDateCustomDay()
    var crsid = ""

    override fun getLayoutId(): Int = R.layout.activity_add_class_attendance_type


    override fun initView() {
        super.initView()
        crsid = intent.getStringExtra(Constant.TITLE)
        bean = intent.getParcelableExtra(Constant.DATA)
        tvAddClassAttendance1.visibility = View.GONE
        tvAddClassAttendance2.visibility = View.GONE
        tvAddClassAttendance5.visibility = View.GONE


        llAddClassAttendance3.setOnClickListener {
            doAdd("0")
        }

        llAddClassAttendance4.setOnClickListener {
            doAdd("2")
        }
    }

    private fun doAdd(s: String) {
        var msg = bean.cno + bean.realname + "\n" + bean.levelname +bean.classname
        mAlert(msg, "请确认学生身份") {
            mViewModel.addAttendance(
                LeaveBean(
                    UserInfo.getUserBean().token, type = "3", status = s,
                    uid = bean.uid!!, atttime = chosenDay, crsid = crsid, remark = "lai"
                )
            )
            finish()
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