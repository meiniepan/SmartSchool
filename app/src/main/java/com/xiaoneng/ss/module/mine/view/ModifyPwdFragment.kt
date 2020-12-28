package com.xiaoneng.ss.module.mine.view

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.CaptchaResponse
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.captchaToast
import com.xiaoneng.ss.common.utils.formatStarPhoneNum
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.common.utils.netResponseFormat
import kotlinx.android.synthetic.main.fragment_modify_pwd.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ModifyPwdFragment : BaseLifeCycleFragment<AccountViewModel>() {
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
        tvConfirmModifyPwd2.setOnClickListener {
            modifyPwd()
        }
        tvSendCaptchaRebind.setOnClickListener {
            doCaptcha()
        }
        etCaptchaRebind.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    doPost()
                }

            }
            return@setOnEditorActionListener false
        }
        etPwdRebind.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    modifyPwd()
                }

            }
            return@setOnEditorActionListener false
        }
        etPhoneRebind.setText(formatStarPhoneNum(UserInfo.getUserBean().phone))
    }

    private fun doCaptcha() {


        mViewModel.onSmsCodeChange(UserInfo.getUserBean().phone)
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
        var vCodeStr = etCaptchaRebind.text.toString()

        if (TextUtils.isEmpty(vCodeStr)) {
            showTip("请输入完整信息")
            return
        }
        mViewModel.verifyVcode(UserInfo.getUserBean().phone,vCodeStr)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }

    private fun modifyPwd() {
        if (etPwdRebind.text.toString().trim().length<8){
            requireContext().mToast(R.string.pwd_too_short)
            return
        }
        var bean = UserInfo.getUserBean()
        bean.password = etPwdRebind.text.toString()
        showLoading()
        mViewModel.modifyUserInfo(bean)
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<CaptchaResponse>(it)?.let {
                    requireContext().captchaToast(it.code)
                }
            }
        })

        mViewModel.mVerifyData.observe(this, Observer {
            it?.let {
                llNewPwd.visibility = View.VISIBLE
                llRebind.visibility = View.GONE
                tvConfirmModifyPwd.visibility = View.GONE
                tvConfirmModifyPwd2.visibility = View.VISIBLE
            }
        })

        mViewModel.mUserInfoData.observe(this, Observer {
            it?.let {
                UserInfo.modifyUserBean(it)
                requireContext().mToast("修改成功")
                activity?.finish()
            }
        })
    }


}