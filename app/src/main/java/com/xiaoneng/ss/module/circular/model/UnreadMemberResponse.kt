package com.xiaoneng.ss.module.circular.model

import com.xiaoneng.ss.module.school.model.UnreadMemberBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/04/28
 * Time: 17:06
 */
data class UnreadMemberResponse(
    var data: ArrayList<UnreadMemberBean>? = null,
    var totle: String? = null,
    var read: String? = null,
    var retotle: String? = null,
    var feedback: String? = null,
    var unread: String? = null,
    var lastid: String? = null
)