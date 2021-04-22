package com.xiaoneng.ss.account.view

import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.AppBean
import com.xiaoneng.ss.account.model.LoginReq
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.activity.MainActivity
import com.xiaoneng.ss.module.mine.view.UserProtocolActivity
import kotlinx.android.synthetic.main.activity_login_tea.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginTeacherActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {

    private var isHideFirst: Boolean = true
    private var isPwdType: Boolean = false
    private var timer: CountDownTimer? = null
    var isTeacher = true
    private var isAgree: Boolean by SPreference(Constant.AGREE_PROTOCOL, false)

    override fun getLayoutId() = R.layout.activity_login_tea

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        isTeacher = intent.getBooleanExtra(Constant.FLAG, true)
        iv_eye.setOnClickListener(this)
        tvSwitchType.setOnClickListener(this)
        tvSendCaptchaTeacher.setOnClickListener(this)
        tvLoginTeacher.setOnClickListener(this)
        tvSwitchIdTeacher.setOnClickListener(this)
        tvProtocolRegister.setOnClickListener(this)
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
        etPwd.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    doLogin()
                }

            }
            return@setOnEditorActionListener false
        }
        cbProtocol.isChecked = isAgree
        cbProtocol.setOnCheckedChangeListener { buttonView, isChecked ->
            isAgree = isChecked
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
            R.id.tvLoginTeacher -> {
                doLogin()
            }

            R.id.tvProtocolRegister -> {
                mStartActivity<UserProtocolActivity>(this)
            }
        }
    }

    private fun doLogin() {
        var phoneStr = etPhoneTeacher.text.toString()
        var vCodeStr = etCaptchaTeacher.text.toString()
        var pwdStr = etPwd.text.toString()
        if (!isAgree) {
            showTip("请先同意用户协议及隐私政策")
        } else {
            if (isPwdType) {
                if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(pwdStr)) {
                    showTip("请输入完整信息")
                    return
                }
                showLoading()
                if (isTeacher) {
                    mViewModel.login(2, LoginReq(phoneStr, spassword = pwdStr))
                } else {
                    mViewModel.login(3, LoginReq(phoneStr, spassword = pwdStr))
                }
            } else {
                if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
                    showTip("请输入完整信息")
                    return
                }
                showLoading()
                if (isTeacher) {
                    mViewModel.login(2, LoginReq(phoneStr, vCodeStr))
                } else {
                    mViewModel.login(3, LoginReq(phoneStr, vCodeStr))
                }
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
                captchaToast(it.code)
            }
        })

        mViewModel.mLoginData.observe(this, Observer {
            it?.let {
                UserInfo.loginSuccess(it)
                Handler().postDelayed(
                    {
                        mViewModel.getApps()
                        showLoading()
                    }, 100
                )

            }
        })
        mViewModel.mAppData.observe(this, Observer {
            it?.let {
                netResponseFormat<ArrayList<AppBean>>(it)?.let { bean ->
                    AppInfo.modifyAppInfo(bean)
                    mToast(R.string.login_success)
                    mStartActivity<MainActivity>(this)
                }
            }
        })
    }

}