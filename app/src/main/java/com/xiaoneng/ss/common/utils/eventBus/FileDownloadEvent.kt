package com.xiaoneng.ss.common.utils.eventBus

import com.xiaoneng.ss.module.school.model.DiskFileBean
import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/05/18
 * Time: 18:46
 */
class FileDownloadEvent(val file:DiskFileBean) {
    fun post() {
        EventBus.getDefault().post(this)
    }
    fun postSticky() {
        EventBus.getDefault().postSticky(this)
    }
}