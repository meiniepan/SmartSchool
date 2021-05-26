package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.eventBus.OnPushEvent
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.model.MyPushBean
import com.xiaoneng.ss.module.circular.adapter.SysMsgAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.view.AchievementActivity
import com.xiaoneng.ss.module.school.view.AttendanceActivity
import com.xiaoneng.ss.module.school.view.TaskDetailActivity
import com.xiaoneng.ss.module.school.view.TimetableActivity
import kotlinx.android.synthetic.main.activity_system_msg.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class SystemMsgActivity : BaseLifeCycleActivity<CircularViewModel>() {
    private var lastId: String? = null
    lateinit var mAdapter: SysMsgAdapter
    var mData: ArrayList<NoticeBean> = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_system_msg
    }

    override fun initView() {
        super.initView()
        initAdapter()
        tvCleanMsg.setOnClickListener {
            showLoading()
            mViewModel.readAll()
        }
    }

    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    override fun getData() {
        super.getData()
        mViewModel.getNoticeList(type = "system", lastid = lastId)
    }

    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvSysMsg.setNoMoreData(false)
        rvSysMsg.showLoadingView()
        getData()
    }

    private fun initAdapter() {
        rvSysMsg.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
            }
        })
        mAdapter = SysMsgAdapter(R.layout.item_sys_msg, mData)
        rvSysMsg.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SystemMsgActivity)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mViewModel.read(mData[position].id!!, status = "1")
            when (mData[position].action) {
                //1同步新任务 2任务日志被驳回或通过 3考勤更新 4时令更替 5成绩更新 6发布新版本
                "1" -> {
                    mStartActivity<TaskDetailActivity>(this) {
                        putExtra(Constant.ID, mData[position].actioninfo)
                        putExtra(Constant.TYPE, "1")
                    }
                }
                "2" -> {
                    mStartActivity<TaskDetailActivity>(this) {
                        putExtra(Constant.ID, mData[position].actioninfo)
                        putExtra(Constant.TYPE, "1")
                    }
                }
                "3" -> {
                    mStartActivity<AttendanceActivity>(this)
                }
                "4" -> {
                    mStartActivity<TimetableActivity>(this)
                }
                "5" -> {
                    mStartActivity<AchievementActivity>(this)
                }
                "6" -> {
                    mToast("已经是最新版本")
                }
            }
//            mStartActivity<NoticeDetailActivity>(this) {
//                putExtra(Constant.TITLE, mData!![position].title)
//                putExtra(Constant.ID, mData!![position].id)
//            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                rvSysMsg.finishRefreshLoadMore()
                it.data?.let {
                    if (it.size > 0) {
                        tvCleanMsg.visibility = View.VISIBLE
                        lastId = it.last().id
                        mData.addAll(it)
                    } else {
                        if (lastId != null) {
                            rvSysMsg.showFinishLoadMore()
                        } else {
                            tvCleanMsg.visibility = View.GONE
                        }
                    }
                    rvSysMsg.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                getData()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshPush(event: OnPushEvent) {
        doRefresh()
    }
}