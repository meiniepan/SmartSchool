package com.xiaoneng.ss.common.state

import android.content.Context
import com.xiaoneng.ss.common.state.callback.CollectListener

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:31
 */
interface UserState {
    fun collect(context: Context, position: Int, listener: CollectListener)

    fun login(context: Context)

}