package com.xiaoneng.ss.common.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/27
 * Time: 18:46
 */
class ChangeThemeEvent {
    fun post() {
        EventBus.getDefault().post(this)
    }
}