package com.xiaoneng.ss.account.view

import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.LoginReq
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import kotlinx.android.synthetic.main.activity_login_tea.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginTeacherActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {


    private var timer: CountDownTimer? = null

    override fun getLayoutId() = R.layout.activity_login_tea

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        setStatusBarDark()
        tvSendCaptchaTeacher.setOnClickListener(this)
        tvLoginTeacher.setOnClickListener(this)
        tvSwitchIdTeacher.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSwitchIdTeacher -> {
                mStartActivity<LoginSwitchActivity>(this)
            }

            R.id.tvSendCaptchaTeacher -> {
                var phoneStr = etPhoneTeacher.text.toString()
                if (!RegexUtils.isMobileSimple(phoneStr)) {
                    showTip("请输入正确手机号")
                    return
                }
                mViewModel.captchaTeacher(phoneStr)
                tvSendCaptchaTeacher.isEnabled = false
                timer = object : CountDownTimer(60 * 1000, 1000) {
                    override fun onFinish() {
                        tvSendCaptchaTeacher.text = "发送验证码"
                        tvSendCaptchaTeacher.isEnabled = true
                    }

                    override fun onTick(p0: Long) {
                        var mm = p0 / 1000
                        tvSendCaptchaTeacher.text = "发送验证码 $mm"
                    }
                }.start()
            }

            R.id.tvLoginTeacher -> {
                var phoneStr = etPhoneTeacher.text.toString()
                var vCodeStr = etCaptchaTeacher.text.toString()

                    if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
                        showTip("请输入完整信息")
                        return
                    }

                mViewModel.loginTeacher(LoginReq(phoneStr, vCodeStr))
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }

    override fun initDataObserver() {
        mViewModel.mCaptchaData.observe(this, Observer {
            it?.let {
                toast(it.code)
            }
        })
    }

}