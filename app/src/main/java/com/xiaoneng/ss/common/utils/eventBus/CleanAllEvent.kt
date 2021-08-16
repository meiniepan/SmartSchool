package com.xiaoneng.ss.common.utils.eventBus

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/02/25
 * Time: 18:46
 */
class CleanAllEvent(val unread:String?=null) {
    fun post() {
        EventBus.getDefault().post(this)
    }
}