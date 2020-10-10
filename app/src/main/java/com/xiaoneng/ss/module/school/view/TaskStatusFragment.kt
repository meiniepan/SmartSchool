package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.adapter.TaskStatusAdapter
import com.xiaoneng.ss.module.school.model.TaskDetailBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_task_status.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class TaskStatusFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    private var lastId: String? = null
    private var status: String? = null
    private var mType: String? = null//1接收 2发布
    lateinit var mAdapter: TaskStatusAdapter
    var mData = ArrayList<TaskDetailBean>()

    override fun getLayoutId(): Int = R.layout.fragment_task_status

    companion object {
        fun getInstance(): Fragment {
            return TaskStatusFragment()
        }

    }

    override fun initView() {
        status = arguments?.getString(Constant.TASK_STATUS)
        mType = arguments?.getString(Constant.TYPE)
        super.initView()
        initAdapter()
    }

    override fun getData() {
        //任务状态0待发布1进行中2完成3关闭
        if (status == "-1") {
            mViewModel.getTaskList(lastid = lastId)
        } else {
            if (mType == "1") {
                mViewModel.getTaskList(status = status, lastid = lastId)
            } else if (mType == "2") {
                mViewModel.getPublishTaskList(status = status, lastid = lastId)
            }
        }
    }

    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvTaskStatus.showLoadingView()
        rvTaskStatus.setNoMoreData(false)
        getData()
    }


    private fun initAdapter() {
        rvTaskStatus.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
            }
        })
        mAdapter = TaskStatusAdapter(R.layout.item_task_status, mData)
        mAdapter.setType(mType ?: "")
        rvTaskStatus.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            if (mType == "2" && status == "0") {
                mStartActivity<AddTaskActivity>(context) {
                    putExtra(Constant.ID, mData[position].id)
                    putExtra(Constant.TYPE, mType)
                }
            } else {
                mStartActivity<TaskDetailActivity>(context) {
                    putExtra(Constant.ID, mData[position].id)
                    putExtra(Constant.TYPE, mType)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        doRefresh()
    }


    override fun initDataObserver() {
        mViewModel.mTaskListData.observe(this, Observer { response ->
            response?.let {
                rvTaskStatus.finishRefreshLoadMore()
                it.data?.let {
                    if (it.size > 0) {
                        lastId = it.last().id
                        mData.addAll(it)
                    } else {
                        if (lastId != null) {
                            rvTaskStatus.showFinishLoadMore()
                        }
                    }
                    rvTaskStatus.notifyDataSetChanged()
                }
            }
        })

    }


}