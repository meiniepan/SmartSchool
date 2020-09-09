package com.xiaoneng.ss.module.circular.view

import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_notice_detail.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class NoticeDetailActivity : BaseLifeCycleActivity<CircularViewModel>() {
    var bean: NoticeBean? = null
    private var timer: CountDownTimer? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_notice_detail
    }

    override fun initView() {
        super.initView()
        bean = intent.getParcelableExtra(Constant.DATA)
        tvRead.setOnClickListener {
            doRead()
        }
        tvNoticeTitle.text = bean?.title
        tvNoticeInfo.text = bean?.remark
        tvTime1.text = DateUtil.formatShowTime(bean?.noticetime!!)
        initReceivedUI()
    }

    private fun initReceivedUI() {
        if (bean?.type == "feedback") {
            if (bean?.received == "1") {
                tvRead.visibility = View.GONE
                ivHasRead.visibility = View.VISIBLE
            } else {
                tvRead.visibility = View.VISIBLE
                ivHasRead.visibility = View.GONE
                preRead()
            }
        }
    }

    private fun doRead() {
        mViewModel.read(bean?.id!!, received = "1")
    }

    override fun initData() {
        super.initData()
        mViewModel.getNoticeDetail(bean?.id!!)
    }

    private fun preRead() {

        timer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onFinish() {
                tvRead.text = "我已阅读"
                tvRead.isEnabled = true
                tvRead.setTextColor(resources.getColor(R.color.white))
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvRead.text = "我已阅读（${mm}s）"
                tvRead.isEnabled = false
                tvRead.setTextColor(resources.getColor(R.color.commonHint))
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }

    override fun initDataObserver() {
        mViewModel.mNoticeDetail.observe(this, Observer { response ->
            response?.let {
                tvName.text = it.data.send_username
                tvReadNum.text = it.read + "/" + it.total
            }
        })

        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                tvRead.visibility = View.GONE
                ivHasRead.visibility = View.VISIBLE
            }
        })
    }
}
