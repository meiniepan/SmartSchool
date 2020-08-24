package com.xiaoneng.ss.module.school.view

import cn.addapp.pickers.picker.DateTimePicker
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.getDatePick
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class RejectActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private val pick: DateTimePicker by lazy {
        getDatePick(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_reject


    override fun initView() {
        super.initView()

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