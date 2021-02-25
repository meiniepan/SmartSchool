package com.xiaoneng.ss.common.utils.eventBus

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/02/25
 * Time: 18:46
 */
class RefreshUnreadEvent(val unread:String?) {
    fun post() {
        EventBus.getDefault().post(this)
    }
}