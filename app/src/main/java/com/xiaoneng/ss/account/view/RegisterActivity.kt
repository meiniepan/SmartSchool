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
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.mine.view.UserProtocolActivity
import kotlinx.android.synthetic.main.activity_login_tea.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.cbProtocol
import kotlinx.android.synthetic.main.activity_register.tvProtocolRegister
import org.jetbrains.anko.toast

class RegisterActivity : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    private var isHideFirst: Boolean = true
    private var isPwdType: Boolean = true
    private var timer: CountDownTimer? = null
    private var isAgree: Boolean by SPreference(Constant.AGREE_PROTOCOL_REG,false)

    override fun getLayoutId(): Int = R.layout.activity_register

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        super.initView()
        tvSendCaptchaRegister.setOnClickListener(this)
        ivEyeRegiter.setOnClickListener(this)
        tvRegister.setOnClickListener(this)
        tvSwitchIdRegister.setOnClickListener(this)
        tvProtocolRegister.setOnClickListener(this)
        cbProtocol.isChecked = isAgree
        cbProtocol.setOnCheckedChangeListener { buttonView, isChecked ->
            isAgree = isChecked
        }
    }

    override fun initDataObserver() {
        mViewModel.mCaptchaData.observe(this, Observer {
            it?.let {
                captchaToast(it.code)
            }
        })
        mViewModel.mRegisterData.observe(this, Observer {
            it?.let{
                mToast(R.string.register_success)
                mStartActivity<LoginStuActivity>(this)
                finish()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSwitchIdRegister -> {
                mStartActivity<LoginSwitchActivity>(this)
            }

            R.id.tvProtocolRegister -> {
                mStartActivity<UserProtocolActivity>(this)
            }

            R.id.tvRegister -> {
                doRegister()
            }

            R.id.ivEyeRegiter -> {
                doEye()
            }

            R.id.tvSendCaptchaRegister -> {
                dpCaptcha()
            }
        }
    }

    private fun doRegister() {
        var phoneStr = etPhoneRegister.text.toString()
        var vCodeStr = etCaptchaRegister.text.toString()
        var inviteStr = etInviteRegister.text.toString()
        var nameStr = etNameRegister.text.toString()
        var pwdStr = etPwdRegister.text.toString()
        var cno = etCnoRegister.text.toString()
        var sno = etSnoRegister.text.toString()
        var eduid = etEduRegister.text.toString()
        if (!isAgree) {
            showTip("请先同意用户协议及隐私政策")
        } else {
            if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr) || TextUtils.isEmpty(
                    inviteStr
                )
            ) {
                showTip("请输入完整信息")
                return
            }

            if (etPwdRegister.text.toString().trim().length < 8) {
                mToast(R.string.pwd_too_short)
                return
            }

            mViewModel.registerCo(
                RegisterReq(phoneStr, vCodeStr, inviteStr, nameStr, pwdStr,
                cno=cno,sno=sno,eduid=eduid)
            )
        }
    }

    private fun dpCaptcha() {
        var phoneStr = etPhoneRegister.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr)) {
            showTip("请输入正确手机号")
            return
        }
        mViewModel.captcha(1,phoneStr)
        tvSendCaptchaRegister.isEnabled = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tvSendCaptchaRegister.text = "发送验证码"
                tvSendCaptchaRegister.isEnabled = true
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvSendCaptchaRegister.text = "发送验证码 $mm"
            }
        }.start()
    }

    private fun doEye() {
        if (isHideFirst) {
            ivEyeRegiter.setImageResource(R.drawable.ic_eye);
            //密文
            var method1: HideReturnsTransformationMethod =
                HideReturnsTransformationMethod.getInstance()
            etPwdRegister.transformationMethod = method1;
            isHideFirst = false;
        } else {
            ivEyeRegiter.setImageResource(R.drawable.ic_eye_no);
            //密文
            var method: TransformationMethod =
                PasswordTransformationMethod.getInstance();
            etPwdRegister.transformationMethod = method;
            isHideFirst = true;

        }
        // 光标的位置
        val index: Int = etPwdRegister.text.toString().length
        etPwdRegister.setSelection(index)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }
}
