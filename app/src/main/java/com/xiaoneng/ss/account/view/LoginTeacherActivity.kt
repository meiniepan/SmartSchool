package com.xiaoneng.ss.account.view

import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.LoginReq
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login_tea.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginTeacherActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {


    private var timer: CountDownTimer? = null
    var isTeacher = true

    override fun getLayoutId() = R.layout.activity_login_tea

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        isTeacher = intent.getBooleanExtra(Constant.FLAG, true)
        tvSendCaptchaTeacher.setOnClickListener(this)
        tvLoginTeacher.setOnClickListener(this)
        tvSwitchIdTeacher.setOnClickListener(this)
        UserInfo.getUserBean().phone?.let {
            if (isTeacher) {
                etPhoneTeacher.setText(UserInfo.getUserBean().phone)
            } else {
                etPhoneTeacher.setText(UserInfo.getUserBean().parentphone)
            }
        }
        if (isTeacher) {
            tvType.text = "老师"
        } else {
            tvType.text = "家长"
        }
        etCaptchaTeacher.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    doLogin()
                }

            }
            return@setOnEditorActionListener false
        }
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
                if (isTeacher) {
                    mViewModel.captcha(2, phoneStr)
                } else {
                    mViewModel.captcha(3, phoneStr)
                }
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
                etCaptchaTeacher.isFocusable = true
                etCaptchaTeacher.isFocusableInTouchMode = true
            }

            R.id.tvLoginTeacher -> {
                doLogin()
            }

        }
    }

    private fun doLogin() {
        var phoneStr = etPhoneTeacher.text.toString()
        var vCodeStr = etCaptchaTeacher.text.toString()

        if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
            showTip("请输入完整信息")
            return
        }
        if (isTeacher) {
            mViewModel.login(2, LoginReq(phoneStr, vCodeStr))
        } else {
            mViewModel.login(3, LoginReq(phoneStr, vCodeStr))
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

        mViewModel.mLoginData.observe(this, Observer {
            it?.let {
                UserInfo.loginSuccess(it)
                toast(R.string.login_success)
                mStartActivity<MainActivity>(this)
            }
        })
    }

}