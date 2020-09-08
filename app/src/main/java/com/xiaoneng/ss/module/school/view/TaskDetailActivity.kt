package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.school.adapter.TaskLogAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task_detail.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class TaskDetailActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    var id:String = ""
    lateinit var mAdapter: TaskLogAdapter
    var mData: ArrayList<NoticeBean>? = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_task_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
        ctbNoticeDetail.setTitle(intent.getStringExtra("title"))
        tvAddLogTaskDetail.setOnClickListener {
            mStartActivity<AddLogActivity>(this)
        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = TaskLogAdapter(R.layout.item_task_log, mData)
        rvTaskDetail.apply {
            layoutManager = LinearLayoutManager(this@TaskDetailActivity)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 20f).toInt()))
            adapter = mAdapter
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tvAction1 ->{
                    mStartActivity<RejectActivity>(this)
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        mData?.add(NoticeBean("2"))
        mData?.add(NoticeBean("2"))
    }
    override fun initDataObserver() {
//        mViewModel.mNoticeDetail.observe(this, Observer { response ->
//            response?.let {
//
//            }
//        })
    }

}