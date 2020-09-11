package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_apply_leave.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ApplyLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_apply_leave


    override fun initView() {
        super.initView()
        llItem1ApplyLeave.setOnClickListener {
            //病假
            mStartActivity<LeaveTypeActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "2")
            }
        }
        llItem2ApplyLeave.setOnClickListener {
            //事假
            mStartActivity<LeaveTypeActivity>(this) {
                putExtra(Constant.LEAVE_TYPE, "1")
            }
        }
    }


    override fun initDataObserver() {


    }

}