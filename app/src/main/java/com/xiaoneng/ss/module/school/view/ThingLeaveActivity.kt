package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
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
    var mTime = 2


    override fun getLayoutId(): Int = R.layout.activity_thing_leave


    override fun initView() {
        super.initView()
        tvItem8ApplyLeave.text = mTime.toString()
        tvLeftItem8ApplyLeave.setOnClickListener {
            doMinus()
        }
        tvRightItem8ApplyLeave.setOnClickListener {
            doPlus()
        }

    }

    private fun doPlus() {
        mTime += 1
        tvItem8ApplyLeave.text = mTime.toString()
    }

    private fun doMinus() {
        if (mTime > 0) {
            mTime -= 1
            tvItem8ApplyLeave.text = mTime.toString()
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


}