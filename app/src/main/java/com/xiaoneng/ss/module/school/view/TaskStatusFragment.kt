package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.view.NoticeDetailActivity
import com.xiaoneng.ss.module.school.adapter.TaskStatusAdapter
import com.xiaoneng.ss.module.school.model.TaskBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_task_status.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class TaskStatusFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    lateinit var mAdapter: TaskStatusAdapter
    var mData = ArrayList<TaskBean>()

    override fun getLayoutId(): Int = R.layout.fragment_task_status

    companion object {
        fun getInstance(): Fragment {
            return TaskStatusFragment()
        }

    }

    override fun initView() {
        super.initView()
        initAdapter()

    }

    private fun initAdapter() {
        mAdapter = TaskStatusAdapter(R.layout.item_task_status, mData)
        rvTaskStatus.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 10f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

            mStartActivity<NoticeDetailActivity>(context) {
                putExtra(Constant.TITLE, mData[position].taskname)
                putExtra(Constant.ID, mData[position].id)
            }
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.getTaskList()
    }


    override fun initDataObserver() {
        mViewModel.mTaskListData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    showEmpty()
                }
            }
        })

    }


}