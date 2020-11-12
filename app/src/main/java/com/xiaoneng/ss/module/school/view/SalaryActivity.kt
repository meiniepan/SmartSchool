package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.SalaryAdapter
import com.xiaoneng.ss.module.school.model.SalaryListBean
import com.xiaoneng.ss.module.school.model.SalaryResponse
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_salary.*

/**
 * @author Burning
 * @description:工资条
 * @date :2020/10/23 3:17 PM
 */
class SalaryActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: SalaryAdapter
    var mData: ArrayList<SalaryListBean> = ArrayList()
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
//        mViewModel.getSalaryDetail("34")
        mViewModel.getSalaryList()
        mViewModel.getSalaryCaptcha()
    }

    private fun initAdapter() {

        mAdapter = SalaryAdapter(R.layout.item_salary_list, mData)
        rvSalary.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SalaryActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<SalaryCaptchaActivity>(this) {
                putExtra(Constant.ID, mData[position].id)
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<SalaryResponse>(it)?.let {
                    it.data?.let { it1 ->
                        mData.addAll(it1)
                        rvSalary.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}