package com.xiaoneng.ss.module.circular.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
@Parcelize
data class NoticeBean(
    var id: String?,
    var noticeid: String? = "",
    var taskname: String?= "",
    var uid: String?= "",
    var usertype: String?= "",
    var taskinfo: String?= "",
    var status: String?= "",
    var received: String?= "",
    var title: String?= "",
    var noticetime: String?= "",
    var remark: String?= "",
    var type: String?= "",
    var send_uid: String?= "",
    var send_username: String?= "",
    var schoolname: String?= "",
    var schoolid: String?= "",
    var action: String?= "",//1同步新任务 2任务日志被驳回或通过 3考勤更新 4时令更替 5成绩更新 6发布新版本
    var actioninfo: String?= "",
    var operatorname: String?= ""
):Parcelable
