package com.xiaoneng.ss.account.view

import android.os.Build
import android.os.CountDownTimer
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.RegisterReq
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    private var isHideFirst: Boolean = true
    private var isPwdType: Boolean = true
    private var timer: CountDownTimer? = null

    override fun getLayoutId(): Int = R.layout.activity_register

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        setStatusBarDark()
        tvSendCaptcha_register.setOnClickListener(this)
        iv_eye_register.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
        tvSwitchIdRegister.setOnClickListener(this)
        tvProtocolRegister.setOnClickListener(this)

    }

    override fun initDataObserver() {
        mViewModel.mCaptchaData.observe(this, Observer {
            it?.let {
                toast(it.code)
            }
        })
        mViewModel.mRegisterData.observe(this, Observer {
            it?.let{
                toast(R.string.register_success)
                mStartActivity<LoginStuActivity>(this)
            }
            finish()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSwitchIdRegister -> {
                mStartActivity<LoginSwitchActivity>(this)
            }

            R.id.tvProtocolRegister -> {
                toast(getString(R.string.not_open))
            }

            R.id.tvRegister -> {
                doRegister()
            }

            R.id.iv_eye_register -> {
                doEye()
            }

            R.id.tvSendCaptcha_register -> {
                dpCaptcha()
            }
        }
    }

    private fun doRegister() {
        var phoneStr = et_phone_register.text.toString()
        var vCodeStr = et_captcha_register.text.toString()
        var inviteStr = et_invite_register.text.toString()
        var nameStr = et_name_register.text.toString()
        var pwdStr = et_pwd_register.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr) || TextUtils.isEmpty(
                inviteStr
            )
        ) {
            showTip("请输入完整信息")
            return
        }
        mViewModel.registerCo(
            RegisterReq(phoneStr, vCodeStr, inviteStr, nameStr, pwdStr)
        )
    }

    private fun dpCaptcha() {
        var phoneStr = et_phone_register.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr)) {
            showTip("请输入正确手机号")
            return
        }
        mViewModel.captcha(phoneStr)
        tvSendCaptcha_register.isEnabled = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tvSendCaptcha_register.text = "发送验证码"
                tvSendCaptcha_register.isEnabled = true
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvSendCaptcha_register.text = "发送验证码 $mm"
            }
        }.start()
    }

    private fun doEye() {
        if (isHideFirst) {
            iv_eye_register.setImageResource(R.drawable.ic_eye);
            //密文
            var method1: HideReturnsTransformationMethod =
                HideReturnsTransformationMethod.getInstance()
            et_pwd_register.transformationMethod = method1;
            isHideFirst = false;
        } else {
            iv_eye_register.setImageResource(R.drawable.ic_eye_no);
            //密文
            var method: TransformationMethod =
                PasswordTransformationMethod.getInstance();
            et_pwd_register.transformationMethod = method;
            isHideFirst = true;

        }
        // 光标的位置
        val index: Int = et_pwd_register.text.toString().length
        et_pwd_register.setSelection(index)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }
}
