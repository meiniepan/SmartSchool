package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
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
    private var lastId: String? = null
    lateinit var mAdapter: SalaryAdapter
    var mData: ArrayList<SalaryListBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_salary
    }

    override fun initView() {
        super.initView()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvSalary.showLoadingView()
        rvSalary.setNoMoreData(false)
        getData()
    }


    override fun getData() {
        super.getData()
        mViewModel.getSalaryList(type = "nopage",lastid = lastId)
    }

    private fun initAdapter() {
        rvSalary.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
            }
        })
        mAdapter = SalaryAdapter(R.layout.item_salary_list, mData)
        rvSalary.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SalaryActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (mData.get(position)?.read!="1"){
                mViewModel.readSalaryDetail(mData.get(position)?.id)
            }
            mStartActivity<SalaryDetailActivity>(this) {
                putExtra(Constant.ID, mData.get(position)?.id)
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mSalaryListData.observe(this, Observer {
            it?.let {
                rvSalary.finishRefreshLoadMore()
                netResponseFormat<SalaryResponse>(it)?.let { bean ->
                    bean.data?.let {
                        if (it.size > 0) {
                            lastId = it.last().id
                            mData.addAll(it)
                        } else {
                            if (lastId != null) {
                                rvSalary.showFinishLoadMore()
                            }
                        }
                        rvSalary.notifyDataSetChanged()
                    }
                }
            }
        })
        mViewModel.mSalaryReadData.observe(this, Observer {
            it?.let {

            }
        })
    }
}