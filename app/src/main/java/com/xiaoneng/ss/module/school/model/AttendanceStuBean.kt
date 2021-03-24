package com.xiaoneng.ss.module.school.model

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
data class AttendanceStuBean(
    var name: String? = "",
    var unit: String? = "",
    var total: String? = "",

    var id: String? = "",
    var realname: String? = "",
    var uid: String? = "",
    var usertype: String? = "",
    var type: String? = "",//考勤类型1早考勤2早到班3课堂考勤
    var status: String? = "",//状态0迟到1已完成2旷课3请假
    var leavetype: String? = "",//请假类型1事假2病假3传染病
    var remark: String? = "",
    var classname: String? = "",
    var levelname: String? = "",
    var atttime: String? = "",
    var courseid: String? = "",
    var leaveid: String? = "",
    var stleave: String? = "",
    var etleave: String? = "",
    var attendances: String? = ""


    ) : Parcelable