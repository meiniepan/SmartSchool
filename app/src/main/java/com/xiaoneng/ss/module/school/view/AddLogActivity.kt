package com.xiaoneng.ss.module.school.view

import cn.addapp.pickers.picker.DateTimePicker
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.getDatePick
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_task.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AddLogActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<NoticeBean>()
    private val pick: DateTimePicker by lazy {
        getDatePick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_add_log


    override fun initView() {
        super.initView()
        initAdapter()

    }

    private fun showBegin() {
        pick.setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                var time = "$year-$month-$day $hour:$minute"
                tvBeginAddTask.text = time
            }

        })
        pick.show()

    }



    private fun initAdapter() {
//        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
//        rvParticipant.apply {
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(RecycleViewDivider(context, dp2px(context, 82f).toInt()))
//            adapter = mAdapter
//        }
//        mAdapter.setOnItemClickListener { _, view, position ->
//
//        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
//        mViewModel.mNoticeData.observe(this, Observer { response ->
//            response?.let {
//                mData.clear()
//                mData.addAll(it.data)
//                if (mData.size > 0) {
//                    mAdapter.notifyDataSetChanged()
//                } else {
//                    showEmpty()
//                }
//            }
//        })

    }


}