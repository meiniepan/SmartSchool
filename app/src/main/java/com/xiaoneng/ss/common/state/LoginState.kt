package com.xiaoneng.ss.common.state

import android.content.Context
import com.xiaoneng.ss.common.state.callback.CollectListener

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:32
 */
class LoginState : UserState {

    override fun collect(context: Context, position: Int, listener: CollectListener) {
        listener.collect(position)
    }

    override fun login(context: Context) {}


}