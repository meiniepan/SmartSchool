package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_notice_detail.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class NoticeDetailActivity : BaseLifeCycleActivity<CircularViewModel>(), View.OnClickListener {
    var id:String = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_notice_detail
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
        ctbNoticeDetail.setTitle(intent.getStringExtra("title"))
    }

    override fun initData() {
        super.initData()
        mViewModel.getNoticeDetail(id)
    }
    override fun initDataObserver() {
        mViewModel.mNoticeDetail.observe(this, Observer { response ->
            response?.let {

            }
        })
    }

    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.tvSwitchId -> {
////                mStartActivity<LoginSwitchActivity>(this){
////                    putExtra("title",md)
////                }
//            }
//
//        }
    }
}