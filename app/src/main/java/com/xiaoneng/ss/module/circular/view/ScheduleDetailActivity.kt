package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_schedule_detail.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class ScheduleDetailActivity : BaseLifeCycleActivity<CircularViewModel>() {
    var bean: ScheduleBean? = ScheduleBean()
    var beginTime: String? = ""
    var endTime: String? = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_detail
    }

    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)
        bean?.let {
            beginTime = it.scheduletime
            endTime = it.scheduleover
            etDetailSchedule.apply {
                if (it.remark.isNullOrEmpty()) {
                    this.visibility = View.GONE
                } else {
                    etDetailSchedule.setText(it.remark)
                }
            }
            tvTitleScheduleDetail.text = it.title
            DateUtil.showTimeFromNet(it.scheduletime!!, tvBeginDate, tvBeginTime)
            DateUtil.showTimeFromNet(it.scheduleover!!, tvEndDate, tvEndTime)
        }


        ivAction1Schedule.setOnClickListener {
            onEdit()
        }
        ivAction2Schedule.setOnClickListener {
            onDelete()
        }

    }

    private fun onDelete() {
        mAlert(getString(R.string.deleteSure)){
        showLoading()
        mViewModel.deleteSchedule(bean)
        }
    }

    private fun onEdit() {
        mStartActivity<AddScheduleActivity>(this) {
            putExtra(Constant.TITLE, true)
            putExtra(Constant.DATA, bean)
        }

    }


    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        mViewModel.mAddScheduleData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                mToast(R.string.deal_done)
                finish()
            }
        })
    }


}