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
data class AttCourseBean(
    var id: String? = "",
    var cno: String? = "",
    var coursename: String? = "",
    var type: String? = "",
    var total: String? = "",
    var remark: String? = "",
    var classroomid: String? = "",
    var classroomname: String? = "",
    var teacheruid: String? = "",
    var teachername: String? = "",
    var semesterid: String? = "",
    var classid: String? = "",
    var groupid: String? = "",
    var semester: String? = "",
    var campusid: String? = "",
    var campus: String? = "",//校区
    var level: String? = "",
    var classname: String? = "",
    var weekday: String? = "",
    var weeks: String? = "",
    var hour: String? = "",
    var minute: String? = "",
    var hmin: String? = "",
    var coursetime: String? = "",
    var schoolid: String? = "",
    var schoolname: String? = "",
    var muser_id: String? = "",
    var cuser_id: String? = "",
    var issingle: String? = "",
    var createtime: String? = "",
    var updatetime: String? = "",
    var position: String? = "",
    var isattendances: String? = "",
    var attlists: MutableList<AttendanceStuBean>? = ArrayList(),
    var checked: Boolean = false


    ):Parcelable