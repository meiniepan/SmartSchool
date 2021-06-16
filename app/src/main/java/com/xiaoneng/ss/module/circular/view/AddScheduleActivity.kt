package com.xiaoneng.ss.module.circular.view

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.adapter.ChooseColorAdapter
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.view.AddInvolveActivity
import kotlinx.android.synthetic.main.activity_add_schedule.*
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
    val mData by lazy { ColorUtil.getCustomColors() }
    var time: Long = System.currentTimeMillis()
    var beginTime: String? = ""
    var endTime: String? = ""
    var bean: ScheduleBean? = ScheduleBean()
    var isModify = false
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var mDataClasses = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true


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
                    //当结束时间小于开始时间时
                    if (endTime.isNullOrEmpty() || DateUtil.getTimeInMillis(beginTime) > DateUtil.getTimeInMillis(
                            endTime
                        )
                    ) {
                        DateUtil.showTimeFromNet(
                            DateUtil.getNearTimeBeginYear(
                                DateUtil.getTimeInMillis(
                                    beginTime
                                )
                            ), tvEndDate, tvEndTime
                        )
                        endTime = DateUtil.getNearTimeBeginYear(DateUtil.getTimeInMillis(beginTime))
                    }
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(
                    tvEndDate,
                    tvEndTime,
                    beginTime = DateUtil.getTimeInMillis(beginTime)
                ) {
                    endTime = this
                    //当结束时间小于开始时间时
                    if (beginTime.isNullOrEmpty() || DateUtil.getTimeInMillis(beginTime) > DateUtil.getTimeInMillis(
                            endTime
                        )
                    ) {
                        DateUtil.showTimeFromNet(endTime!!, tvBeginDate, tvBeginTime)
                        beginTime = endTime
                    }
                }
            }
        }
        if (UserInfo.getUserBean().usertype == "1") {
            llInvite.visibility = View.GONE
        } else {
            llInvite.visibility = View.VISIBLE
        }
        llInvite.setOnClickListener {
            mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_COURSE) {
                putExtra(Constant.DATA, mDataDepartment)
                putExtra(Constant.DATA2, mDataClasses)
                //从草稿箱第一次选择参与人，传入原有参与人数据
//                if (isFirst) {
//                    if (receiveList.size > 0) {
//                        putExtra(Constant.DATA3, receiveList)
//                    }
//                }
            }
        }
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
            mToast("请完善信息")
            return
        }

        bean!!.token = UserInfo.getUserBean().token
        bean!!.title = etThemeAddSchedule.text.toString()
        bean!!.scheduletime = beginTime
        bean!!.scheduleover = endTime
        bean!!.remark = etRemarkAddSchedule.text.toString()
        bean!!.involve = Gson().toJson(involves)
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
            }
            mData[position].isCheck = true
            adapter.notifyDataSetChanged()
            chosenColor = mData[position].color

        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COURSE) {
                if (data != null) {
                    isFirst = false
                    involves.clear()
                    receiveList.clear()
                    mDataDepartment = data.getParcelableArrayListExtra(Constant.DATA)!!
                    mDataClasses = data.getParcelableArrayListExtra(Constant.DATA2)!!
                    mDataDepartment.forEach {
                        addDepartment(it)
                    }
                    mDataClasses.forEach {
                        addDepartment(it)
                    }
                    dealData()
                }
            }
        }
    }

    private fun dealData() {
        var str = ""
        if (receiveList.size > 0) {
            receiveList.forEach {
                str = str + it.realname + "、"
                involves.add(it)
            }
            str = str.substring(0, str.length - 1)
        }
        tvPeople.text = str
    }

    private fun addDepartment(it: DepartmentBean) {
        if (it.num!!.toInt() > 0) {
            it.list.forEach {
                receiveList.add(
                    UserBeanSimple(
                        uid = it.uid,
                        realname = it.realname,
                        classid = it.classid,
                        usertype = it.usertype
                    )
                )
            }
        }
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
