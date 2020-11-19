package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/28 3:17 PM
 */
class PropertyRecordActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    private var chosenDevice: String =  ""

    override fun getLayoutId(): Int {
        return R.layout.activity_add_property
    }

    override fun initView() {
        super.initView()
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