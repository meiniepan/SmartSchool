package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.adapter.PerformanceStuAdapter
import com.xiaoneng.ss.module.school.model.PerformanceBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_performance_master.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class PerformanceStuActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PerformanceStuAdapter
    var mData: ArrayList<PerformanceBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_performance_stu
    }

    override fun initView() {
        super.initView()
        tvAction1.setOnClickListener {

        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = PerformanceStuAdapter(R.layout.item_performance_stu, mData)
        rvPerformance?.apply {
            layoutManager = LinearLayoutManager(this@PerformanceStuActivity)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 20f).toInt()))
            adapter = mAdapter
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tvAction1 ->{
                    mStartActivity<RejectActivity>(this)
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        mData.add(PerformanceBean(""))
        mData.add(PerformanceBean(""))
        mData.add(PerformanceBean(""))
        mData.add(PerformanceBean(""))
//        mViewModel.getPerformance()
    }
    override fun initDataObserver() {
        mViewModel.mPerformanceData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    showEmpty()
                }
            }
        })
    }

}