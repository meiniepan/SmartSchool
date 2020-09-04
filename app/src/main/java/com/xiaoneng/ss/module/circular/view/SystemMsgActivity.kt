package com.xiaoneng.ss.module.circular.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.adapter.SysMsgAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_system_msg.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class SystemMsgActivity : BaseLifeCycleActivity<CircularViewModel>() {
    lateinit var mAdapter: SysMsgAdapter
    var mData: ArrayList<NoticeBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_system_msg
    }

    override fun initView() {
        super.initView()
        initAdapter()
    }

    override fun initData() {
        super.initData()
        rvSysMsg.showLoadingView()
        mViewModel.getNoticeList()
    }

    private fun initAdapter() {
        rvSysMsg.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                rvSysMsg.finishRefreshLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                initData()
            }
        })
        mAdapter = SysMsgAdapter(R.layout.item_sys_msg, mData)
        rvSysMsg.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SystemMsgActivity)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mStartActivity<NoticeDetailActivity>(this) {
                putExtra(Constant.TITLE, mData!![position].title)
                putExtra(Constant.ID, mData!![position].id)
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                rvSysMsg.finishRefreshLoadMore()
                mData.clear()
                for (i in it.data) {
                    if (i.type == "system")
                        mData.add(i)
                    rvSysMsg.notifyDataSetChanged()
                }
            }
        })
    }
}