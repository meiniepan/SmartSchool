package com.xiaoneng.ss.common.utils.eventBus

import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.model.FolderBean
import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/05/18
 * Time: 18:46
 */
class FileMoveEvent(val file: FolderBean) {
    fun post() {
        EventBus.getDefault().post(this)
    }
    fun postSticky() {
        EventBus.getDefault().postSticky(this)
    }
}