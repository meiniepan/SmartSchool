package com.xiaoneng.ss.module.mine.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SysSettingActivity : BaseLifeCycleActivity<CircularViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_sys_setting


    override fun initView() {
        super.initView()

    }



    override fun initData() {
        super.initData()
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