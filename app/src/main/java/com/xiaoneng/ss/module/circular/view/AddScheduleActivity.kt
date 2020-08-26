package com.xiaoneng.ss.module.circular.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import cn.addapp.pickers.picker.DateTimePicker
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.ColorUtil
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.getDatePick
import com.xiaoneng.ss.module.circular.adapter.ChooseColorAdapter
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_add_schedule.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AddScheduleActivity : BaseLifeCycleActivity<CircularViewModel>() {
    private  var chosenColor: String = ""
    lateinit var mAdapter: ChooseColorAdapter
    val mData by lazy { ColorUtil.getCustomColors() }

    private val pick: DateTimePicker by lazy {
        getDatePick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_add_schedule


    override fun initView() {
        super.initView()
        tvBeginAddSchedule.setOnClickListener {
            showBegin()
        }
        tvStopAddSchedule.setOnClickListener {
            showStop()
        }
        initAdapter()
    }

    private fun initAdapter() {
        mAdapter = ChooseColorAdapter(R.layout.item_choose_color, mData)

        rvChooseColor.apply {
            layoutManager = GridLayoutManager(context, 6)

            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            for (i in mData){
                if (i.isCheck){
                    i.isCheck = false
                }
                mData[position].isCheck = true
                adapter.notifyDataSetChanged()
                chosenColor = mData[position].color
            }
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
                tvBeginAddSchedule.text = time
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
                tvStopAddSchedule.text = time
            }

        })
        pick.show()

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