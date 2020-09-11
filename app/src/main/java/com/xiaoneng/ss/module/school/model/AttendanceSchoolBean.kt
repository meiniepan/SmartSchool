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
data class AttendanceSchoolBean(
    var schoolname: String? = "",
    var morninglate: String? = "",
    var courselate: String? = "",
    var truant: String? = "",
    var thingleave: String? = "",
    var sickleave: String? = "",
    var morninglatelist: MutableList<AttendanceStuBean>? = ArrayList(),
    var courselatelist: MutableList<AttendanceStuBean>? = ArrayList(),
    var truantlist: MutableList<AttendanceStuBean>? = ArrayList(),
    var thingleavelist: MutableList<AttendanceStuBean>? = ArrayList(),
    var sickleavelist: MutableList<AttendanceStuBean>? = ArrayList()


) : Parcelable