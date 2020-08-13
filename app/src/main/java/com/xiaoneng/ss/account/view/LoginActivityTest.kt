package com.xiaoneng.ss.module.account.view

import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.ChangeThemeEvent
import com.xiaoneng.ss.common.utils.ColorUtil
import com.xiaoneng.ss.common.utils.startActivity
import com.xiaoneng.ss.module.account.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.Subscribe

class LoginActivityTest : BaseLifeCycleActivity<AccountViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initView() {
        super.initView()
        button_login.setOnClickListener(this)
        register_text.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        initColor()
        showSuccess()
    }


    private fun initColor() {
        login_background.setBackgroundColor(ColorUtil.getColor(this))
        button_login.setTextColor(ColorUtil.getColor(this))
    }

    override fun initDataObserver() {
        mViewModel.mLoginData.observe(this, Observer {
            it?.let { loginResponse ->
                UserInfo.instance.loginSuccess(
                    loginResponse.username,
                    loginResponse.id.toString(),
                    loginResponse.collectIds
                )
                finish()
            }
        })
    }

    override fun showCreateReveal(): Boolean = true

    override fun showDestroyReveal(): Boolean = false

    override fun onBackPressed() = finish()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login -> {
                mViewModel.loginCo("18810472753","5848", "123456")
//                mViewModel.registerCo("aa","aa","aa")
            }
            R.id.register_text -> {
                startActivity<RegisterActivity>(this)
                finish()
            }
            R.id.ivBack -> {
                onBackPressed()
            }
        }
    }

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }
}
