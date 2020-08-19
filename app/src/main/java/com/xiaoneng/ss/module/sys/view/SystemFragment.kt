package com.xiaoneng.ss.module.sys.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.circular.view.SchoolFragment
import com.xiaoneng.ss.module.sys.adapter.SystemAdapter
import com.xiaoneng.ss.module.circular.viewmodel.SchoolViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SystemFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    protected lateinit var mAdapter: SystemAdapter

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

    override fun getLayoutId(): Int = R.layout.fragment_article_list


}