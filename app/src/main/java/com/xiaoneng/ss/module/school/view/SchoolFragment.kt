package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.model.SalaryResponse
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_school.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class SchoolFragment : BaseLifeCycleFragment<SchoolViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_school

    companion object {
        fun getInstance(): Fragment {
            return SchoolFragment()
        }

    }

    override fun initView() {
        super.initView()
        llTask.setOnClickListener {
            mStartActivity<TaskActivity>(context)
        }
        llTimeTable.setOnClickListener {
            var bb = UserInfo.getUserBean()
            mStartActivity<TimetableActivity>(context)

        }
        llAttendance.setOnClickListener {
            mStartActivity<AttendanceActivity>(context)
        }
        llPerformance.setOnClickListener {
            mStartActivity<AchievementActivity>(context)

        }
        llProperty.setOnClickListener {
            mStartActivity<PropertyActivity>(context)
        }
        llSalary.setOnClickListener {
            showLoading()
            mViewModel.getSalaryList()
        }
        llCloudDisk.setOnClickListener {
            mStartActivity<CloudDiskActivity>(context)
        }

    }


    override fun initDataObserver() {
        mViewModel.mSalaryListData.observe(this, Observer {
            it?.let {
                netResponseFormat<SalaryResponse>(it)?.let {
                    mStartActivity<SalaryActivity>(requireContext()) {
                        putExtra(Constant.DATA, it.data)
                    }
                }
            }
        })
    }


}