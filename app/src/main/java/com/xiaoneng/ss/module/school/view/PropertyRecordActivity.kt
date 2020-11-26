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
import com.xiaoneng.ss.module.school.adapter.PropertyRecordAdapter
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.PropertyDetailResp
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_record.*

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/28 3:17 PM
 */
class PropertyRecordActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var lastId: String? = null
    lateinit var mAdapter: PropertyRecordAdapter
    var mData: ArrayList<PropertyDetailBean> = ArrayList()
    private var mType: String? = null//0报修 1维修
    var typeStr = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_property_record
    }

    override fun initView() {
        super.initView()
        mType = intent.getStringExtra(Constant.TYPE)
        if (mType == "0") {
            typeStr = "report"
        } else if (mType == "1") {
            typeStr = "repairer"
        }
        initAdapter()
    }


    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvPropertyRecord.showLoadingView()
        rvPropertyRecord.setNoMoreData(false)
        getData()
    }


    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    override fun getData() {
        super.getData()
        mViewModel.getPropertyRecord(type = typeStr,lastid = lastId)
    }

    private fun initAdapter() {
        rvPropertyRecord.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
            }
        })
        mAdapter = PropertyRecordAdapter(R.layout.activity_property_detail, mData)
        mAdapter.setType(mType)
        rvPropertyRecord.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<AddBookSiteActivity>(this)
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                rvPropertyRecord.finishRefreshLoadMore()
                netResponseFormat<PropertyDetailResp>(it)?.let { bean ->
                    bean.data?.let {
                        if (it.size > 0) {
                            lastId = it.last().id
                            mData.addAll(it)
                        } else {
                            if (lastId != null) {
                                rvPropertyRecord.showFinishLoadMore()
                            }
                        }
                        rvPropertyRecord.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}