package com.xiaoneng.ss.common.utils.eventBus

import android.util.Log
import com.xiaoneng.ss.model.MyPushBean
import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/05/22
 * Time: 18:46
 */
class OnPushEvent(val event:MyPushBean) {
    fun post() {
        Log.w("=====", "post")
        EventBus.getDefault().post(this)
    }
    fun postSticky() {
        EventBus.getDefault().postSticky(this)
    }
}