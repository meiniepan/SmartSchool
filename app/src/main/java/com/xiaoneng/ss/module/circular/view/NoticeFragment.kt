package com.xiaoneng.ss.module.circular.view

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.eventBus.OnPushEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.adapter.NoticeAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_notice.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class NoticeFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private var lastId: String? = null
    private var readPosition: Int = 0
    lateinit var mAdapter: NoticeAdapter
    var mData = ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.fragment_notice

    companion object {
        fun getInstance(circularFragment: CircularFragment): Fragment {
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
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
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
                mViewModel.read(mData[position].id!!, "1")
                readPosition = position
            }
            mStartActivity<NoticeDetailActivity>(context) {
                putExtra(Constant.DATA, mData[position])
            }
        }
    }

    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvNotice.showLoadingView()
        rvNotice.setNoMoreData(false)
        getData()
    }


    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    override fun getData() {
        mViewModel.getNoticeList(lastid = lastId)
    }

    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                rvNotice.finishRefreshLoadMore()
                it.data?.let {
                    if (it.size > 0) {
                        lastId = it.last().id
                        mData.addAll(it)
                    } else {
                        if (lastId != null) {
                            rvNotice.showFinishLoadMore()
                        }
                    }
                    rvNotice.notifyDataSetChanged()
                }
                RefreshUnreadEvent(it.unread).post()
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                mData[readPosition].status = "1"
                rvNotice.notifyDataSetChanged()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshPush(event: OnPushEvent) {
        doRefresh()
    }
}