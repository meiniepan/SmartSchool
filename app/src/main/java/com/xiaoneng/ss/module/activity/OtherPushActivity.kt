package com.xiaoneng.ss.module.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import com.umeng.message.UmengNotifyClickActivity
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin

/**
 * @author Burning
 * @description:
 * @date :2020/10/21 6:24 PM
 */
class OtherPushActivity : UmengNotifyClickActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_splash)
        if (TextUtils.isEmpty(UserInfo.getUserBean().token)) {
            mainLogin(this)
        } else {
            mStartActivity<MainActivity>(this)
        }
        finish()
    }

    override fun onMessage(p0: Intent?) {
        super.onMessage(p0)
    }
}