package com.xiaoneng.ss.module.circular.view

import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.activity.MainActivity
import com.xiaoneng.ss.module.circular.adapter.ChooseColorAdapter
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_add_schedule.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddScheduleActivity : BaseLifeCycleActivity<CircularViewModel>() {
    private var chosenColor: String = "#C7000B"
    lateinit var mAdapter: ChooseColorAdapter
    var time: Long = System.currentTimeMillis()
    var beginTime: String? = ""
    var endTime: String? = ""
    val mData by lazy { ColorUtil.getCustomColors() }
    var bean: ScheduleBean? = ScheduleBean()
    var isModify = false


    override fun getLayoutId(): Int = R.layout.activity_add_schedule


    override fun initView() {
        super.initView()
        isModify = intent.getBooleanExtra(Constant.TITLE, false)
        if (isModify) {
            bean = intent.getParcelableExtra(Constant.DATA)
            bean?.let {
                initUI(it)
            }
        }
        initTime()
        beginTime = DateUtil.getNearTimeBeginYear(time)
        endTime = DateUtil.getNearTimeEndYear(time)
        tvActionTitle.setOnClickListener {
            addSchedule()
        }
        DateUtil.showTimeFromNet(DateUtil.getNearTimeBeginYear(time), tvBeginDate, tvBeginTime)
        DateUtil.showTimeFromNet(DateUtil.getNearTimeEndYear(time), tvEndDate, tvEndTime)
        llBeginTime.apply {
            setOnClickListener {
                showDatePick(tvBeginDate, tvBeginTime) {
                    beginTime = this
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(tvEndDate, tvEndTime) {
                    endTime = this
                }
            }
        }
//        initAdapter()
    }

    private fun initUI(it: ScheduleBean) {
        etThemeAddSchedule.setText(it.title)
        DateUtil.showTimeFromNet(it.scheduletime!!, tvBeginDate, tvBeginTime)
        DateUtil.showTimeFromNet(it.scheduleover!!, tvEndDate, tvEndTime)
        etRemarkAddSchedule.setText(it.remark)
        chosenColor = it.color!!
        mData.forEach {
            it.isCheck = it.color == it.color
        }
    }

    private fun initTime() {
        time = intent.getLongExtra(Constant.TIME, System.currentTimeMillis())
        var calNow = Calendar.getInstance()
        var calInput = Calendar.getInstance()
        calInput.timeInMillis = time
        var calNew = Calendar.getInstance()
        calNew.set(Calendar.YEAR, calInput.get(Calendar.YEAR))
        calNew.set(Calendar.MONTH, calInput.get(Calendar.MONTH))
        calNew.set(Calendar.DAY_OF_MONTH, calInput.get(Calendar.DAY_OF_MONTH))
        calNew.set(Calendar.HOUR_OF_DAY, calNow.get(Calendar.HOUR_OF_DAY))
        calNew.set(Calendar.MINUTE, calNow.get(Calendar.MINUTE))
        time = calNew.timeInMillis
    }

    private fun addSchedule() {
        if (
            TextUtils.isEmpty(etThemeAddSchedule.text.toString()) ||
            TextUtils.isEmpty(tvBeginDate.text.toString())

        ) {
            toast("请完善信息")
            return
        }

        bean!!.token = UserInfo.getUserBean().token
        bean!!.title = etThemeAddSchedule.text.toString()
        bean!!.scheduletime = beginTime
        bean!!.scheduleover = endTime
        bean!!.remark = etRemarkAddSchedule.text.toString()
        bean!!.color = chosenColor
        showLoading()
        if (isModify) {
            mViewModel.modifySchedule(bean)
        } else {
            mViewModel.addSchedule(bean)
        }
    }

    private fun initAdapter() {
        mAdapter = ChooseColorAdapter(R.layout.item_choose_color, mData)

        rvChooseColor.apply {
            layoutManager = GridLayoutManager(context, 6)

            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            for (i in mData) {
                if (i.isCheck) {
                    i.isCheck = false
                }
                mData[position].isCheck = true
                adapter.notifyDataSetChanged()
                chosenColor = mData[position].color
            }
        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
        mViewModel.mAddScheduleData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                toast(R.string.deal_done)
                mStartActivity<MainActivity>(this)
            }
        })

    }


}
