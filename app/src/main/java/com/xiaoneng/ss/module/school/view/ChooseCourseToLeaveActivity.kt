package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.showDateDayPick
import com.xiaoneng.ss.module.school.adapter.ChooseCourseAdapter
import com.xiaoneng.ss.module.school.model.LessonBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_choose_course_leave.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ChooseCourseToLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: ChooseCourseAdapter
    var mData: ArrayList<LessonBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomMmDay()

    override fun getLayoutId(): Int = R.layout.activity_choose_course_leave


    override fun initView() {
        super.initView()
        tvChooseDay.apply {
            text = chosenDay
            setOnClickListener {
                showDateDayPick(this){
                    chosenDay = this
                }
            }

        }

        initAdapter()

    }


    override fun initData() {
        super.initData()
        mViewModel.getTimetable(time = chosenDay)
    }

    private fun initAdapter() {
        mAdapter = ChooseCourseAdapter(R.layout.item_choose_course, mData)
        rvChooseCourse.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }
    override fun initDataObserver() {
        mViewModel.mTimetableData.observe(this, Observer { response ->
            response?.let {
//                mData.clear()
//                mData.addAll(it.list.)
//                if (mData.size > 0) {
//                    mAdapter.notifyDataSetChanged()
//                } else {
//                    showEmpty()
//                }
            }
        })

    }


}