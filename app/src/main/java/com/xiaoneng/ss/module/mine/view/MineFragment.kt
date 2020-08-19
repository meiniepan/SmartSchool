package com.xiaoneng.ss.module.mine.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.module.mine.adapter.MineAdapter

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    protected lateinit var mAdapter: MineAdapter

    companion object {
        fun getInstance(): MineFragment? {
            return MineFragment()
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