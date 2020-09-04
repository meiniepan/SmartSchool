package com.xiaoneng.ss.module.mine.view

import android.os.CountDownTimer
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.model.ParentBean
import kotlinx.android.synthetic.main.activity_add_parent.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AddParentActivity : BaseLifeCycleActivity<AccountViewModel>() {
    private var timer: CountDownTimer? = null
    override fun getLayoutId(): Int = R.layout.activity_add_parent


    override fun initView() {
        super.initView()
        tvConfirmBind.setOnClickListener {
            doBind()
        }
        tvSendCaptchaBindParent.setOnClickListener {
            doCaptcha()
        }
    }

    private fun doCaptcha() {
        var phoneStr = etPhoneBindParent.text.toString()
        if (!RegexUtils.isMobileSimple(phoneStr)) {
            showTip("请输入正确手机号")
            return
        }

        mViewModel.captcha(Constant.BIND_PARENT_SMS, phoneStr)
        tvSendCaptchaBindParent.isEnabled = false
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tvSendCaptchaBindParent.text = "发送验证码"
                tvSendCaptchaBindParent.isEnabled = true
            }

            override fun onTick(p0: Long) {
                var mm = p0 / 1000
                tvSendCaptchaBindParent.text = "发送验证码 $mm"
            }
        }.start()
    }

    private fun doBind() {
        var phoneStr = etPhoneBindParent.text.toString()
        var vCodeStr = etCaptchaBindParent.text.toString()

        if (!RegexUtils.isMobileSimple(phoneStr) || TextUtils.isEmpty(vCodeStr)) {
            showTip("请输入完整信息")
            return
        }
        showLoading()
        mViewModel.bindParent(phoneStr, vCodeStr)
    }


    override fun initDataObserver() {
        mViewModel.mCaptchaData.observe(this, Observer {
            it?.let {
                toast(it.code)
            }
        })

        mViewModel.mParentsData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
                val jsonString = gson.toJson(it)
                val resultType = object : TypeToken<ArrayList<ParentBean>>() {}.type
                gson.fromJson<ArrayList<ParentBean>>(jsonString,resultType)?.let {
                    toast("绑定成功")
                    UserInfo.modifyParents(it)
                    finish()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let { it.cancel() }
    }
}