package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/11/13
 * Time: 17:06
 */
data class AddBookSiteBody(
    var token: String? = null,
    var id: String? = null,
    var roomid: String? = null,
    var ostime: String? = null,
    var oetime: String? = null,
    var remark: String? = null,
    var status: String? = null,
    var os_position: String? = null,
    var oe_position: String? = null
)