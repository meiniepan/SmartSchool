package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_property
    }

    override fun initView() {
        super.initView()
        tvPropertyApply1.setOnClickListener {
            mStartActivity<PropertyApplyActivity>(this)
        }
        tvPropertyApply2.setOnClickListener {
            mStartActivity<PropertyApplyActivity>(this)
        }
        tvPropertyRecord1.setOnClickListener {
            mStartActivity<PropertyApplyActivity>(this)
        }
        tvPropertyRecord2.setOnClickListener {
            mStartActivity<PropertyApplyActivity>(this)
        }
        initAdapter()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun initAdapter() {


    }

    override fun initDataObserver() {

    }
}