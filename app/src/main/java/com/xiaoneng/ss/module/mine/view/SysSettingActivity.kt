package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.AppManager
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.activity_sys_setting.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SysSettingActivity : BaseLifeCycleActivity<MineViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_sys_setting


    override fun initView() {
        super.initView()
        llItem4Setting.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        mViewModel.logout()
        showLoading()
    }


    override fun initData() {
        super.initData()
    }


    override fun initDataObserver() {
        mViewModel.mLogoutData.observe(this, Observer { response ->
            response?.let {
                UserInfo.logoutSuccess()
                when (UserInfo.getUserBean().usertype) {
                    "1" -> {
                        mStartActivity<LoginStuActivity>(this)
                    }
                    "2" -> {
                        mStartActivity<LoginTeacherActivity>(this)
                    }
                    else -> {
                        mStartActivity<LoginSwitchActivity>(this)
                    }
                }
            }
        })

    }


}