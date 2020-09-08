package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
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
            mStartActivity<SickLeaveActivity>(this)
        }
        llItem2ApplyLeave.setOnClickListener {
            mStartActivity<ThingLeaveActivity>(this)
        }
    }


    override fun initDataObserver() {


    }

}