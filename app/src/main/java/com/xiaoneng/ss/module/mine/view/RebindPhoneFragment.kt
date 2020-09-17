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
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import kotlinx.android.synthetic.main.fragment_rebind.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class RebindPhoneFragment : BaseLifeCycleFragment<AccountViewModel>() {
    var showRebindLayout: Boolean = false
    private var timer: CountDownTimer? = null

    override fun getLayoutId(): Int = R.layout.fragment_rebind

    companion object {
        fun getInstance(): Fragment {
            return RebindPhoneFragment()
        }

    }

    override fun initView() {
        super.initView()
        if (UserInfo.getUserBean().usertype == "1" &&
            UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_PAR
        ) {
            tvCurrentPhone.text = UserInfo.getUserBean().parentphone
        } else {
            tvCurrentPhone.text = UserInfo.getUserBean().phone
        }
        ivNext.setOnClickListener {
            llCurrent.visibility = View.GONE
            llRebind.visibility = View.VISIBLE
        }
        tvConfirmSecure.setOnClickListener {
            doPost()
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
    }

    private fun doCaptcha() {
        var phoneStr = etPhoneRebind.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr)) {
            showTip("请输入正确手机号")
            return
        }

        mViewModel.onSmsCodeChange(phoneStr)
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
            mViewModel.verifyVcode(phoneStr, vCodeStr)
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<CaptchaResponse>(it)?.let {
                    requireContext().toast(it.code)
                }
            }
        })

        mViewModel.mVerifyData.observe(this, Observer {
            it?.let {
                var bean = UserInfo.getUserBean()
                bean.phone = etPhoneRebind.text.toString()
                showLoading()
                mViewModel.modifyUserInfo(bean)
            }
        })

        mViewModel.mUserInfoData.observe(this, Observer {
            it?.let {
                UserInfo.modifyUserBean(it)
                requireContext().toast("修改成功")
                activity?.finish()
            }
        })
    }


}