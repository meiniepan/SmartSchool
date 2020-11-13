package com.xiaoneng.ss.module.school.view

import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.CaptchaResponse
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.model.SalaryResponse
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_salary_captcha.*

/**
 * @author Burning
 * @description:工资条
 * @date :2020/11/12 3:17 PM
 */
class SalaryCaptchaActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var timer: CountDownTimer? = null
    private var id: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_salary_captcha
    }

    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)
        tvConfirm.setOnClickListener {
            doPost()
        }

        tvSendCaptcha.setOnClickListener {
            doCaptcha()
        }
        etCaptcha.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    doPost()
                }

            }
            return@setOnEditorActionListener false
        }

        etPhone.setText(formatStarPhoneNum(UserInfo.getUserBean().phone))
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun doCaptcha() {


        mViewModel.getSalaryCaptcha()
        tvSendCaptcha.isEnabled = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tvSendCaptcha.text = "发送验证码"
                tvSendCaptcha.isEnabled = true
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvSendCaptcha.text = "发送验证码 $mm"
            }
        }.start()

    }

    private fun doPost() {
        var vCodeStr = etCaptcha.text.toString()

        if (TextUtils.isEmpty(vCodeStr)) {
            showTip("请输入完整信息")
            return
        }
        showLoading()
        mViewModel.getTmpToken(vCodeStr)
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<CaptchaResponse>(it)?.let {
                    captchaToast(it.code)
                }
            }
        })

        mViewModel.mTmpTokenData.observe(this, Observer {
            it?.let {
                Handler().postDelayed(
                    {
                        showLoading()
                        mViewModel.getSalaryList()

                    }, 100
                )
            }
        })

        mViewModel.mSalaryListData.observe(this, Observer {
            it?.let {
                netResponseFormat<SalaryResponse>(it)?.let {
                    mStartActivity<SalaryActivity>(this) {
                        putExtra(Constant.DATA, it.data)
                    }
                }
            }
        })
    }
}