package com.xiaoneng.ss.module.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.WindowManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/10 3:02 PM
 */
class SplashActivity  : BaseLifeCycleActivity<AccountViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        var handler = Handler()
        var runnable = Runnable { mInitView() }
        handler.postDelayed(runnable, stt_splash.getDurationTime())
        stt_splash.setOnClickListener {
            handler.removeCallbacks(runnable)
            mInitView()
        }
    }

    override fun initData() {
        super.initData()

    }

    /**
     * 初始化进场动画
     */
    private fun mInitView() {
        startIntent()
    }


    private fun startIntent() {
        finish()
        if (TextUtils.isEmpty(UserInfo.getUserBean().token)) {
            mainLogin(this)
        } else {
            mStartActivity<MainActivity>(this)
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }


    override fun initDataObserver() {

    }
}