package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cn.addapp.pickers.picker.DateTimePicker
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
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
class AddTaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<NoticeBean>()
    private val pick: DateTimePicker by lazy {
        getDatePick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_add_task


    override fun initView() {
        super.initView()
        initAdapter()
        tvBeginAddTask.setOnClickListener {
            showBegin()
        }
        tvStopAddTask.setOnClickListener {
            showStop()
        }
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

    private fun showStop() {
        pick.setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                var time = "$year-$month-$day $hour:$minute"
                tvStopAddTask.text = time
            }

        })
        pick.show()

    }

    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 82f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
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

    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        // 底部弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_save_draft, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvSaveDraft)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvNoSaveDraft)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvCancelDraft)
            .setOnClickListener { v: View? ->

                bottomDialog.dismiss()
            }
    }

}