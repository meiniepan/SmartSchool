package com.xiaoneng.ss.module.circular.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class NoticeResponse(
    var data: ArrayList<NoticeBean>? = null,
    var totle: String? = null,
    var read: String? = null,
    var retotle: String? = null,
    var feedback: String? = null,
    var unread: String? = null,
    var lastid: String? = null
)