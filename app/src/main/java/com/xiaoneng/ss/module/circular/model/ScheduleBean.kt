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
data class ScheduleBean(
    var token: String? = null,
    var id: String? = null,
    var realname: String? = null,
    var type: String? = null,
    var title: String? = null,
    var remark: String? = null,
    var schoolname: String? = null,
    var status: String? = null,
    var usertype: String? = null,
    var uid: String? = null,
    var scheduletime: String? = null,
    var scheduleover: String? = null,
    var day: String? = null,
    var month: String? = null,
    var him: String? = null,
    var cno: String? = null,
    var color: String? = null,
    var schoolid: String? = null,
    var muser_id: String? = null,
    var cuser_id: String? = null
) : Parcelable