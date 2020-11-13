package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.model.SalaryDetailBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:工资条
 * @date :2020/11/13 3:17 PM
 */
class SalaryDetailActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var id: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_salary_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)

    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getSalaryDetail(id)
    }


    override fun initDataObserver() {
        mViewModel.mSalaryDetailData.observe(this, Observer {
            it?.let {
                netResponseFormat<SalaryDetailBean>(it)?.let {
                    toast("ss")
                }
            }
        })
    }
}