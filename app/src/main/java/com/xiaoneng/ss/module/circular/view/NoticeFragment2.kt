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
import com.xiaoneng.ss.module.school.view.*
import kotlinx.android.synthetic.main.fragment_notice.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

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
        fun getInstance(): NoticeFragment2 {
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
            if (mData[position].status != "1") {
                mViewModel.read(mData[position].id!!, status = "1")
            }
            when (mData[position].expand?.action) {
                "admin/spacebook/default" -> {
                    mStartActivity<BookSiteActivity>(requireContext()) {

                    }
                }
                "admin/schedules/default" -> {
                    mStartActivity<ScheduleActivity>(requireContext()) {

                    }
                }
                "moral/moral/default" -> {
                    mStartActivity<QuantizeActivity>(requireContext())
                }
                "admin/wages/default" -> {
                    mStartActivity<SalaryCaptchaActivity>(requireContext())
                }
                "admin/repair/default" -> {
                    mStartActivity<PropertyActivity>(requireContext())
                }
                "admin/tasks/default" -> {
                    mStartActivity<TaskActivity>(requireContext())
                }
                "disk/folder/default" -> {
                    mStartActivity<CloudDiskActivity>(requireContext())
                }
                "admin/attendances/default" -> {
                    mStartActivity<AttendanceActivity>(requireContext())
                }
                "admin/achievements/default" -> {
                    mStartActivity<AchievementActivity>(requireContext())
                }
                "admin/courses/default" -> {
                    mStartActivity<TimetableActivity>(requireContext())
                }
                else -> {
                    mStartActivity<NoticeDetailActivity>(requireContext()){
                        putExtra(Constant.DATA, mData[position])
                    }
                }
            }
//            mStartActivity<NoticeDetailActivity>(this) {
//                putExtra(Constant.TITLE, mData!![position].title)
//                putExtra(Constant.ID, mData!![position].id)
//            }
        }
    }

     fun doRefresh() {
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
                        } else {
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
//                doRefresh()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshPush(event: OnPushEvent) {
        doRefresh()
    }
}