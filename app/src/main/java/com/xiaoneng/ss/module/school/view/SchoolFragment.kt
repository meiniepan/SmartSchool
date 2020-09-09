package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
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

            mStartActivity<TimetableActivity>(context)

        }
        llAttendance.setOnClickListener {
            mStartActivity<AttendanceActivity>(context)
        }
        llPerformance.setOnClickListener {
            when (UserInfo.getUserBean().usertype) {
                "1" -> {
                    mStartActivity<PerformanceStuActivity>(context)
                }
                "2" -> {
                    mStartActivity<PerformanceStuActivity>(context)
                }
                "99" -> {
                    mStartActivity<PerformanceStuActivity>(context)
                }
                else -> {
                    mStartActivity<PerformanceStuActivity>(context)
                }
            }

        }
    }


    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}