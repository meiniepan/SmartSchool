package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.adapter.SalaryAdapter
import com.xiaoneng.ss.module.school.model.SalaryListBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_salary.*

/**
 * @author Burning
 * @description:工资条
 * @date :2020/10/23 3:17 PM
 */
class SalaryActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: SalaryAdapter
    var mData: ArrayList<SalaryListBean>? = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_salary
    }

    override fun initView() {
        super.initView()
        mData = intent.getParcelableArrayListExtra(Constant.DATA)
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

        mAdapter = SalaryAdapter(R.layout.item_salary_list, mData)
        rvSalary.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SalaryActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<SalaryDetailActivity>(this) {
                putExtra(Constant.ID, mData?.get(position)?.id)
            }
        }
    }

    override fun initDataObserver() {

    }
}