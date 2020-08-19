package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ScheduleFragment1 : BaseLifeCycleFragment<CircularViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_schedule_1

    companion object {
        fun getInstance(): Fragment {
            return ScheduleFragment1()
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