package com.xiaoneng.ss.module.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin

/**
 * @author Burning
 * @description:
 * @date :2020/8/10 3:02 PM
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    fun initView() {
        var handler = Handler()
        var runnable = Runnable { startIntent() }
        handler.postDelayed(runnable, 1000)

    }


    private fun startIntent() {
        if (TextUtils.isEmpty(UserInfo.getUserBean().token)) {
            mainLogin(this)
        } else {
            mStartActivity<MainActivity>(this)
        }
        finish()
    }

}