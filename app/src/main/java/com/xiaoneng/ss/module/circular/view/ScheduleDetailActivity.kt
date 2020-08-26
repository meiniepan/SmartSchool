package com.xiaoneng.ss.module.circular.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.adapter.TaskLogAdapter
import kotlinx.android.synthetic.main.activity_schedule_detail.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class ScheduleDetailActivity : BaseLifeCycleActivity<CircularViewModel>() {
    var id:String = ""
    lateinit var mAdapter: TaskLogAdapter
    var mData: ArrayList<NoticeBean>? = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
        ctbDetailSchedule.setTitle(intent.getStringExtra("title"))
        ivAction1Schedule.setOnClickListener {
            onEdit()
        }
        ivAction2Schedule.setOnClickListener {
            onDelete()
        }

    }

    private fun onDelete() {

    }

    private fun onEdit() {

    }


    override fun initData() {
        super.initData()
    }
    override fun initDataObserver() {
//        mViewModel.mNoticeDetail.observe(this, Observer { response ->
//            response?.let {
//
//            }
//        })
    }

}