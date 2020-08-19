package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ScheduleFragment : BaseLifeCycleFragment<SchoolViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_schedule_2

    companion object {
        fun getInstance(): Fragment {
            return ScheduleFragment()
        }

    }

    override fun initView() {
        super.initView()
    }

    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }



}