package com.xiaoneng.ss.module.circular.view

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.showDatePick
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
    var bean: ScheduleBean = ScheduleBean()
    var beginTime: String? = ""
    var endTime: String? = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_detail
    }

    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)
        beginTime = bean.scheduletime
        endTime = bean.scheduleover

        etDetailSchedule.setText(bean.remark)
        tvTitleScheduleDetail.text = bean.title
        DateUtil.showTimeFromNet(bean.scheduletime!!,tvBeginDate,tvBeginTime)
        DateUtil.showTimeFromNet(bean.scheduleover!!,tvEndDate,tvEndTime)
        llBeginTime.apply {
            setOnClickListener {
                showDatePick(tvBeginDate,tvBeginTime) {
                    beginTime = this
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(tvEndDate,tvEndTime) {
                    endTime = this
                }
            }
        }
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
        if (
            TextUtils.isEmpty(tvBeginDate.text.toString())

        ) {
            toast("请完善信息")
            return
        }
        bean.token = UserInfo.getUserBean().token
        bean.scheduletime = beginTime
        bean.scheduleover = endTime
        bean.remark = etDetailSchedule.text.toString()
        showLoading()
        mViewModel.modifySchedule(bean)
    }


    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        mViewModel.mAddScheduleData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                toast("修改成功")
                finish()
            }
        })
    }


}