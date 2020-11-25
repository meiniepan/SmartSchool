package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * @author Burning
 * @description:全校通告
 * @date :2020/11/25 3:17 PM
 */
class NoticeActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_notice
    }

    override fun initView() {
        super.initView()

    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()

    }



    override fun initDataObserver() {

    }
}