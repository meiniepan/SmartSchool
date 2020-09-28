package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.adapter.NoticeAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_notice.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class NoticeFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private var readPosition: Int = 0
    lateinit var mAdapter: NoticeAdapter
    var mData = ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.fragment_notice

    companion object {
        fun getInstance(): Fragment {
            return NoticeFragment()
        }

    }

    override fun initView() {
        super.initView()
        initAdapter()
    }

    private fun initAdapter() {
        rvNotice.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                rvNotice.finishRefreshLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getData()
            }
        })
        mAdapter = NoticeAdapter(R.layout.item_notice, mData)
        rvNotice.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            //"status": "0",   0未读 1已读
            if (mData[position].status == "0") {
                mViewModel.read(mData[position].id!!,  "1")
                readPosition = position
            }
            mStartActivity<NoticeDetailActivity>(context) {
                putExtra(Constant.DATA, mData[position])
            }
        }
    }



    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        rvNotice.showLoadingView()
        mViewModel.getNoticeList()
    }

    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                rvNotice.finishRefreshLoadMore()
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    rvNotice.notifyDataSetChanged()
                } else {
                    rvNotice.showEmptyView()
                }
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                mData[readPosition].status = "1"
                rvNotice.notifyDataSetChanged()
            }
        })
    }


}