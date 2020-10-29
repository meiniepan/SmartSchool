package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.adapter.AchievementStuAdapter
import com.xiaoneng.ss.module.school.model.AchievementBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_salary.*

/**
 * @author Burning
 * @description:工资条
 * @date :2020/10/23 3:17 PM
 */
class SalaryActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: AchievementStuAdapter
    var mData: ArrayList<AchievementBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_salary
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
        mViewModel.getSalaryDetail("17")
    }

    private fun initAdapter() {

        mAdapter = AchievementStuAdapter(R.layout.item_performance_stu, mData)
        rvSalary.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SalaryActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {

    }
}