package com.xiaoneng.ss.module.circular.view

import android.text.TextUtils
import android.widget.TextView
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
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

        ctbDetailSchedule.setTitle(bean.title)
        tvBeginTimeSchedule.text = bean.scheduletime
        tvStopTimeSchedule.text = bean.scheduleover
        etDetailSchedule.setText(bean.remark)

        tvBeginTimeSchedule.apply {
            setOnClickListener {
                showDatePick(this) {
                    beginTime = this
                }
            }
        }
        tvStopTimeSchedule.apply {
            setOnClickListener {
                showDatePick(this) {
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
            TextUtils.isEmpty(tvBeginTimeSchedule.text.toString())

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