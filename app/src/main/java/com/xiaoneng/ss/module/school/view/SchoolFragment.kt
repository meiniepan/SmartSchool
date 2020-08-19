package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.circular.adapter.FragmentCircularAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SchoolFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    lateinit var mAda: FragmentCircularAdapter

    override fun getLayoutId(): Int = R.layout.fragment_school

    companion object {
        fun getInstance(): SchoolFragment? {
            return SchoolFragment()
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