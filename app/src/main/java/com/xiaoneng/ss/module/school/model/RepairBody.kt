package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/11/13
 * Time: 17:06
 */
data class RepairBody(
    var token: String? = null,
    var id: String? = null,
    var remark: String? = null,
    var status: String? = null,
    var fileinfo: String? = null,
    var typeid: String? = null,
    var deviceid: String? = null,
    var repairerid: String? = null,
    var isdelay: String? = null,//是否延期1是0否
    var delayreasons: String? = null,
    var delayer: String? = null,
    var repairlog: String? = null,
    var reporttime: String? = null,
    var handletime: String? = null,
    var completetime: String? = null
)