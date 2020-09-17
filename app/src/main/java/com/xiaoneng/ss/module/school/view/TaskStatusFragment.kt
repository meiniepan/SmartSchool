package com.xiaoneng.ss.module.school.view

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
import com.xiaoneng.ss.module.school.adapter.TaskStatusAdapter
import com.xiaoneng.ss.module.school.model.TaskBean
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
    private var status: String? = null
    private var type: String? = null
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
        type = arguments?.getString(Constant.TYPE)
        super.initView()
        initAdapter()
    }

    private fun initAdapter() {
        rvTaskStatus.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                rvTaskStatus.finishRefreshLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getData()
            }
        })
        mAdapter = TaskStatusAdapter(R.layout.item_task_status, mData)
        rvTaskStatus.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 10f).toInt()))
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

            mStartActivity<TaskDetailActivity>(context) {
                putExtra(Constant.ID, mData[position].id)
            }
        }
    }


    override fun getData() {
        rvTaskStatus.showLoadingView()
        //任务状态0待发布1进行中2完成3关闭
        if (status == "-1") {
            mViewModel.getTaskList()
        } else {
            if (type == "1") {
                mViewModel.getTaskList(status = status ?: "")
            } else if (type == "2"){
                mViewModel.getPublishTaskList(status = status ?: "")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }


    override fun initDataObserver() {
        mViewModel.mTaskListData.observe(this, Observer { response ->
            response?.let {
                rvTaskStatus.finishRefreshLoadMore()
                mData.clear()
                mData.addAll(it.data)
                rvTaskStatus.notifyDataSetChanged()
            }
        })

    }


}