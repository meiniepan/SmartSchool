package com.xiaoneng.ss.module.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.AppBean
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
import com.xiaoneng.ss.common.utils.netResponseFormat

/**
 * @author Burning
 * @description:
 * @date :2020/8/10 3:02 PM
 */
class SplashActivity : BaseLifeCycleActivity<AccountViewModel>() {

    var handler = Handler()
    var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun showCreateReveal(): Boolean {
        return false
    }
    override fun initView() {
        super.initView()
        runnable = Runnable { startIntent() }
        handler.postDelayed(runnable!!, 1000)

    }


    private fun startIntent() {
//        if (TextUtils.isEmpty(UserInfo.getUserBean().token)) {
//            mainLogin(this)
//            finish()
//        } else {
//            mViewModel.getApps()
//        }
        mStartActivity<MyTestActivity>(this)
    }

    override fun onDestroy() {
        runnable?.let { handler.removeCallbacks(it) }
        super.onDestroy()
    }

    override fun initDataObserver() {
        mViewModel.mAppData.observe(this, Observer {
            it?.let {
                netResponseFormat<ArrayList<AppBean>>(it)?.let { bean ->
                    AppInfo.modifyAppInfo(bean)
                    mStartActivity<MainActivity>(this)
                    finish()
                }
            }
        })
    }

}