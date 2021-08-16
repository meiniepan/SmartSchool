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
import com.xiaoneng.ss.common.utils.eventBus.CleanAllEvent
import com.xiaoneng.ss.common.utils.eventBus.OnPushEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.module.circular.adapter.NoticeAdapter
import com.xiaoneng.ss.module.circular.adapter.SysMsgAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.view.AchievementActivity
import com.xiaoneng.ss.module.school.view.AttendanceActivity
import com.xiaoneng.ss.module.school.view.TaskDetailActivity
import com.xiaoneng.ss.module.school.view.TimetableActivity
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
class NoticeFragment2 : BaseLifeCycleFragment<CircularViewModel>() {
    private var lastId: String? = null
    private var readPosition: Int = 0
    lateinit var mAdapter: SysMsgAdapter
    var mData = ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.fragment_notice

    companion object {
        fun getInstance(circularFragment: CircularFragment): Fragment {
            return NoticeFragment2()
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
//        mAdapter = SysMsgAdapter(R.layout.item_notice, mData)
        mAdapter = SysMsgAdapter(R.layout.item_sys_msg, mData)

        rvNotice.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
//        mAdapter.setOnItemClickListener { _, view, position ->
//            //"status": "0",   0未读 1已读
//            if (mData[position].status == "0") {
//                mViewModel.read(mData[position].id!!, "1")
//                readPosition = position
//            }
//            mStartActivity<NoticeDetailActivity>(context) {
//                putExtra(Constant.DATA, mData[position])
//            }
//        }

        mAdapter.setOnItemClickListener { _, view, position ->
            mViewModel.read(mData[position].id!!, status = "1")
            when (mData[position].action) {
                //1同步新任务 2任务日志被驳回或通过 3考勤更新 4时令更替 5成绩更新 6发布新版本
                "1" -> {
                    mStartActivity<TaskDetailActivity>(requireContext()) {
                        putExtra(Constant.ID, mData[position].actioninfo)
                        putExtra(Constant.TYPE, "1")
                    }
                }
                "2" -> {
                    mStartActivity<TaskDetailActivity>(requireContext()) {
                        putExtra(Constant.ID, mData[position].actioninfo)
                        putExtra(Constant.TYPE, "1")
                    }
                }
                "3" -> {
                    mStartActivity<AttendanceActivity>(requireContext())
                }
                "4" -> {
                    mStartActivity<TimetableActivity>(requireContext())
                }
                "5" -> {
                    mStartActivity<AchievementActivity>(requireContext())
                }
                "6" -> {
                    requireContext().mToast("已经是最新版本")
                }
            }
//            mStartActivity<NoticeDetailActivity>(this) {
//                putExtra(Constant.TITLE, mData!![position].title)
//                putExtra(Constant.ID, mData!![position].id)
//            }
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
//        mViewModel.getNoticeList(lastid = lastId)
        mViewModel.getNoticeList(type = "system", lastid = lastId)
    }

    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                rvNotice.finishRefreshLoadMore()
                it.data?.let {
                    if (it.size > 0) {
                        CleanAllEvent("1").post()
                        lastId = it.last().id
                        mData.addAll(it)
                    } else {
                        if (lastId != null) {
                            rvNotice.showFinishLoadMore()
                        }else{
                            CleanAllEvent().post()
                        }
                    }
                    rvNotice.notifyDataSetChanged()
                }
                RefreshUnreadEvent(it.unread).post()
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                doRefresh()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshPush(event: OnPushEvent) {
        doRefresh()
    }
}