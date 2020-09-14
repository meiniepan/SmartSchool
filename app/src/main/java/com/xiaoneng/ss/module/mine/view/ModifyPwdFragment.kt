package com.xiaoneng.ss.module.mine.view

import android.os.CountDownTimer
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_modify_pwd.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ModifyPwdFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    private var timer: CountDownTimer? = null
    override fun getLayoutId(): Int = R.layout.fragment_modify_pwd

    companion object {
        fun getInstance(): Fragment {
            return ModifyPwdFragment()
        }

    }

    override fun initView() {
        super.initView()

        tvConfirmModifyPwd.setOnClickListener {
            doPost()
        }
        tvSendCaptchaRebind.setOnClickListener {
            doCaptcha()
        }
    }

    private fun doCaptcha() {
        var phoneStr = etPhoneRebind.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr)) {
            showTip("请输入正确手机号")
            return
        }

//            mViewModel.captcha(3, phoneStr)
        tvSendCaptchaRebind.isEnabled = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tvSendCaptchaRebind.text = "发送验证码"
                tvSendCaptchaRebind.isEnabled = true
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvSendCaptchaRebind.text = "发送验证码 $mm"
            }
        }.start()

    }

    private fun doPost() {
        var phoneStr = etPhoneRebind.text.toString()
        var vCodeStr = etCaptchaRebind.text.toString()

        if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
            showTip("请输入完整信息")
            return
        }
//            mViewModel.login(2, LoginReq(phoneStr, vCodeStr))
    }

    override fun initDataObserver() {

    }


}