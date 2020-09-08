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
    private var status: String? = ""
    lateinit var mAdapter: TaskStatusAdapter
    var mData = ArrayList<TaskBean>()

    override fun getLayoutId(): Int = R.layout.fragment_task_status

    companion object {
        fun getInstance(): Fragment {
            return TaskStatusFragment()
        }

    }

    override fun initView() {
        status = arguments?.getString(Constant.TASK_STATUS)
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
                putExtra(Constant.TITLE, mData[position].taskname)
                putExtra(Constant.ID, mData[position].id)
            }
        }
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        if (status != "-1") {
            mViewModel.getTaskList(status = status!!)
        } else {
            mViewModel.getTaskList()
        }
    }


    override fun initDataObserver() {
        mViewModel.mTaskListData.observe(this, Observer { response ->
            response?.let {
                rvTaskStatus.finishRefreshLoadMore()
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    rvTaskStatus.notifyDataSetChanged()
                } else {
                    rvTaskStatus.showEmptyView()
                }
            }
        })

    }


}