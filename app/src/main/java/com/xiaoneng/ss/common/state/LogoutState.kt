package com.xiaoneng.ss.common.state

import android.content.Context
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.common.state.callback.CollectListener
import com.xiaoneng.ss.common.utils.mStartActivity
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:39
 */
class LogoutState :UserState {
    override fun collect(context: Context, position: Int, listener: CollectListener) {
        startLoginActivity(context)
    }

    override fun login(context: Context) {
        startLoginActivity(context)
    }



    private fun startLoginActivity(context: Context) {
        context?.let {
            it.toast(it.getString(R.string.please_login))
            mStartActivity<LoginSwitchActivity>(it)
        }
    }

}