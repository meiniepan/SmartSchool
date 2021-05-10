package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.TaskStatusAdapter
import com.xiaoneng.ss.module.school.model.TaskDetailBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class TaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var lastId: String? = null
    private var status: String? = null
    private var mType: String? = null//1接收 2发布
    lateinit var mAdapter: TaskStatusAdapter
    var mData = ArrayList<TaskDetailBean>()
    var titlesP = ArrayList<String>()
    var titlesR = ArrayList<String>()
    var statusP = ArrayList<String>()
    var statusR = ArrayList<String>()
    private lateinit var dialogPublish: Dialog
    private lateinit var dialogReceive: Dialog

    override fun getLayoutId(): Int = R.layout.activity_task


    override fun initView() {
        super.initView()
        //todo 权限 AppInfo.checkRule2("teacher/tasks/add"
        initDialogPublish()
        
        titlesP.add("进行中")
        titlesP.add("草稿箱")
        titlesP.add("已关闭")
        statusP.add("1")
        statusP.add("0")
        statusP.add("3")
        
        titlesR.add("未完成")
        titlesR.add("已完成")
        statusR.add("0")
        statusR.add("1")
        initAdapter()
        if (UserInfo.getUserBean().usertype != "1") {
            ivAddTask.visibility = View.VISIBLE
            llTitlePublish.visibility = View.VISIBLE
        } else {
            ivAddTask.visibility = View.GONE
            llTitlePublish.visibility = View.GONE
        }

        ivAddTask.setOnClickListener {
            mStartActivity<AddTaskActivity>(this)
        }
        tvPublish.setOnClickListener {
            mType = "1"
            tvPublish.setBackgroundResource(R.drawable.bac_blue_bac)
            tvPublish.setTextColor(resources.getColor(R.color.white))
            tvReceive.setBackgroundResource(R.drawable.bac_blue_line_21)
            tvReceive.setTextColor(resources.getColor(R.color.themeColor))
            initPublishData()
        }
        tvReceive.setOnClickListener {
            mType = "2"
            tvReceive.setBackgroundResource(R.drawable.bac_blue_bac)
            tvReceive.setTextColor(resources.getColor(R.color.white))
            tvPublish.setBackgroundResource(R.drawable.bac_blue_line_21)
            tvPublish.setTextColor(resources.getColor(R.color.themeColor))
            initReceiveData()
        }

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
            layoutManager = LinearLayoutManager(this@TaskActivity)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            if (mType == "2" && status == "0") {
                mStartActivity<AddTaskActivity>(this) {
                    putExtra(Constant.ID, mData[position].id)
                    putExtra(Constant.TYPE, mType)
                }
            } else {
                mStartActivity<TaskDetailActivity>(this) {
                    putExtra(Constant.ID, mData[position].id)
                    putExtra(Constant.TYPE, mType)
                }
            }
        }
    }
private fun initPublishData(){
    
}
    private fun initReceiveData(){

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

    
    private fun initDialogPublish() {
        // 底部弹出对话框
        dialogPublish =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogPublish.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogPublish.window!!.setGravity(Gravity.BOTTOM)
        dialogPublish.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titlesP)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(this@TaskActivity, 1f).toInt(),
                    this.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvActionStatus.text = titlesP[position]
            rvTaskStatus.showLoadingView()
            doRefresh()
            dialogPublish.dismiss()
        }

    }

    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvTaskStatus.showLoadingView()
        rvTaskStatus.setNoMoreData(false)
        getData()
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