package com.xiaoneng.ss.module.school.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.common.utils.showDateDayPick
import com.xiaoneng.ss.module.school.adapter.ChooseCourseAdapter
import com.xiaoneng.ss.module.school.model.AttCourseBean
import com.xiaoneng.ss.module.school.model.AttCourseResponse
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_choose_course_leave.*
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ChooseCourseToLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: ChooseCourseAdapter
    var mData: ArrayList<AttCourseBean> = ArrayList()
    var mDataChosen: ArrayList<AttCourseBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()
    var chosenDayNet = DateUtil.formatDateCustomDay()
    lateinit var bean: AttendanceBean

    override fun getLayoutId(): Int = R.layout.activity_choose_course_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text = "今天是" + DateUtil.formatTitleToday()
        bean = intent.getParcelableExtra(Constant.DATA)
        bean?.let {
//            initUI()
        }
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        tvChooseDay.apply {
            text = chosenDay
            setOnClickListener {
                showDateDayPick(this) {
                    chosenDayNet = this
                    getData()
                }
            }

        }

        initAdapter()

    }

    private fun doConfirm() {
        mDataChosen.clear()
        mData.forEach {
            if (it.checked) {
                mDataChosen.add(it)
            }
        }

        setResult(
            Activity.RESULT_OK,
            intent.putParcelableArrayListExtra(Constant.DATA, mDataChosen)
                .putExtra(Constant.TITLE,DateUtil.formatTitleToday(chosenDayNet))
                .putExtra(Constant.TITLE_2,chosenDayNet)
        )
        finish()
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getAttTimetable(time = chosenDayNet,uId = bean.uid?:"")
    }

    private fun initAdapter() {
        mAdapter = ChooseCourseAdapter(R.layout.item_choose_course, mData)
        rvChooseCourse.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mData[position].checked = !mData[position].checked
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun initDataObserver() {
        mViewModel.mAttTimetableData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttCourseResponse>(it)?.let {
                    mData.clear()
                    mData.addAll(it.list)
                    mData.forEach {bean->
                        bean.attlists?.let {
                            if (it.size>0) {
                                bean.checked = true
                            }
                        }
                    }
                    rvChooseCourse.notifyDataSetChanged()
                }

            }
        })

    }


}