package com.xiaoneng.ss.module.circular.view

import android.view.View
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class NoticeDetailActivity : BaseLifeCycleActivity<CircularViewModel>(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_login_stu
    }

    override fun initDataObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSwitchId -> {
                mStartActivity<LoginSwitchActivity>(this)
            }

        }
    }
}