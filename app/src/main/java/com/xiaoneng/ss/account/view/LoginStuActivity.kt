package com.xiaoneng.ss.account.view

import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.LoginReq
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login_stu.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginStuActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {


    private var isHideFirst: Boolean = true
    private var isPwdType: Boolean = false
    private var timer: CountDownTimer? = null

    override fun getLayoutId() = R.layout.activity_login_stu

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        tv_stu_register.setOnClickListener(this)
        iv_eye.setOnClickListener(this)
        tvSwitchType.setOnClickListener(this)
        tvSendCaptcha.setOnClickListener(this)
        tvLogin.setOnClickListener(this)
        tvSwitchId.setOnClickListener(this)
        UserInfo.getUserBean().phone?.let {
            etPhone.setText(UserInfo.getUserBean().phone)
        }
        etCaptcha.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    doLogin()
                }

            }
            return@setOnEditorActionListener false
        }
        etPwd.setOnEditorActionListener { teew, i, keyEvent ->
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
            R.id.tvSwitchId -> {
                mStartActivity<LoginSwitchActivity>(this)
            }
            R.id.tv_stu_register -> {
                mStartActivity<RegisterActivity>(this)
            }
            R.id.iv_eye -> {
                if (isHideFirst) {
                    iv_eye.setImageResource(R.drawable.ic_eye);
                    //密文
                    var method1: HideReturnsTransformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    etPwd.transformationMethod = method1;
                    isHideFirst = false;
                } else {
                    iv_eye.setImageResource(R.drawable.ic_eye_no);
                    //密文
                    var method: TransformationMethod = PasswordTransformationMethod.getInstance();
                    etPwd.transformationMethod = method;
                    isHideFirst = true;

                }
                // 光标的位置
                val index: Int = etPwd.getText().toString().length
                etPwd.setSelection(index)
            }
            R.id.tvSwitchType -> {
                if (isPwdType) {
                    tvSwitchType.text = "密码登录"
                    llPwd.visibility = View.GONE
                    llCaptcha.visibility = View.VISIBLE
                } else {
                    tvSwitchType.text = "验证码登录"
                    llPwd.visibility = View.VISIBLE
                    llCaptcha.visibility = View.GONE
                }
                isPwdType = !isPwdType
            }

            R.id.tvSendCaptcha -> {
                var phoneStr = etPhone.text.toString()
                if (!RegexUtils.isMobileSimple(phoneStr)) {
                    showTip("请输入正确手机号")
                    return
                }
                mViewModel.captcha(1,phoneStr)
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

            R.id.tvLogin -> {
                doLogin()
            }

        }
    }

    private fun doLogin() {
        var phoneStr = etPhone.text.toString()
        var vCodeStr = etCaptcha.text.toString()
        var pwdStr = etPwd.text.toString()
        if (isPwdType) {
            if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(pwdStr)) {
                showTip("请输入完整信息")
                return
            }
        } else {
            if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
                showTip("请输入完整信息")
                return
            }
        }

        mViewModel.login(1, LoginReq(phoneStr, vCodeStr, pwdStr))
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