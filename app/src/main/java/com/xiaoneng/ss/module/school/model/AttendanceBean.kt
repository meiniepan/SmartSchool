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
data class AttendanceBean(
    var id: String? = "",
    var uid: String? = "",
    var cno: String? = "",
    var coursename: String? = "",
    var atttime: String? = "",
    var crsid: String? = "",
    var realname: String? = "",
    var type: String? = "",
    var total: String? = "",
    var remark: String? = "",
    var classroomid: String? = "",
    var classroomname: String? = "",
    var teacheruid: String? = "",
    var teachername: String? = "",
    var semesterid: String? = "",
    var mDate: String? = "",
    var classid: String? = "",
    var groupid: String? = "",
    var semester: String? = "",
    var campusid: String? = "",
    var campus: String? = "",
    var level: String? = "",
    var weekday: String? = "",
    var weeks: String? = "",
    var hour: String? = "",
    var minute: String? = "",
    var hmin: String? = "",
    var coursetime: String? = "",
    var levelname: String? = "",
    var classname: String? = "",
    var schoolid: String? = "",
    var schoolname: String? = "",
    var muser_id: String? = "",
    var cuser_id: String? = "",
    var issingle: String? = "",
    var createtime: String? = "",
    var updatetime: String? = "",
    var has_sickleave: String? = "",
    var has_thingleave: String? = "",
    var has_courselate: String? = "",
    var has_truant: String? = "",
    var has_morninglate: String? = "",
    var attlists: ArrayList<AttendanceStuBean>?,
    var tags: ArrayList<String>? = ArrayList(),
    var showhim: String? = ""

) : Parcelable