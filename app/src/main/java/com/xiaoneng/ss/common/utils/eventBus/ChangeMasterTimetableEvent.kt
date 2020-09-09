package com.xiaoneng.ss.common.utils.eventBus

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/27
 * Time: 18:46
 */
class ChangeMasterTimetableEvent(val flag:Boolean) {
    fun post() {
        EventBus.getDefault().post(this)
    }
}