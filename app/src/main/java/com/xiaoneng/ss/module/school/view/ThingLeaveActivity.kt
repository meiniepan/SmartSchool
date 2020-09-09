package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.mStartForResult
import com.xiaoneng.ss.module.school.adapter.AttLessonAdapter
import com.xiaoneng.ss.module.school.model.LessonBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_thing_leave.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ThingLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: AttLessonAdapter
    var mData: ArrayList<LessonBean> = ArrayList()

    override fun getLayoutId(): Int = R.layout.activity_thing_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text  = "今天是"+ DateUtil.formatTitleToday()
        llItem8ApplyLeave.setOnClickListener {
            mStartForResult<ChooseCourseToLeaveActivity>(this, Constant.REQUEST_CODE_LESSON)
        }
        llAttLesson.visibility = View.GONE
        initAdapter()
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }

    private fun initAdapter() {
        mAdapter = AttLessonAdapter(R.layout.item_timetable_title, mData)

        rvAttLesson.apply {
            layoutManager =
                object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

                }
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_LESSON && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mData.clear()
                mData.addAll(data.getParcelableArrayListExtra<LessonBean>(Constant.DATA))
                if (mData.size > 0) {
                    rvAttLesson.notifyDataSetChanged()
                    llAttLesson.visibility = View.VISIBLE
                } else {
                    llAttLesson.visibility = View.GONE
                }
            }
        }
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