package com.xiaoneng.ss.module.school.view

import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.SalaryDetailAdapter
import com.xiaoneng.ss.module.school.model.SalaryDetailBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_salary_detail.*

/**
 * @author Burning
 * @description:工资条
 * @date :2020/11/13 3:17 PM
 */
class SalaryDetailActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var id: String? = null
    lateinit var mAdapter: SalaryDetailAdapter
    var mData: ArrayList<String>? = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_salary_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
        initAdapter()
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

    private fun initAdapter() {

        mAdapter = SalaryDetailAdapter(R.layout.item_salary_detail, mData)
        rvSalaryDetail.apply {
            layoutManager = LinearLayoutManager(this@SalaryDetailActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {
        mViewModel.mSalaryDetailData.observe(this, Observer {
            it?.let {
                netResponseFormat<SalaryDetailBean>(it)?.let { bean ->
                    tvReal.text = bean.reachwages
                    tvRealText.text = bean.title+"实发工资"
                    bean.expand?.keys?.let {
                        mAdapter.setNewData(it)
                        mAdapter.setEdata(bean)
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}