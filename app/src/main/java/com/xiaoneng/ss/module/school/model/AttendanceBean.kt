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
    var id: String? = null,
    var uid: String? = null,
    var cno: String? = null,
    var coursename: String? = null,
    var atttime: String? = null,
    var crsid: String? = null,
    var realname: String? = null,
    var type: String? = null,
    var total: String? = null,
    var remark: String? = null,
    var classroomid: String? = null,
    var classroomname: String? = null,
    var teacheruid: String? = null,
    var teachername: String? = null,
    var semesterid: String? = null,
    var mDate: String? = null,
    var classid: String? = null,
    var groupid: String? = null,
    var semester: String? = null,
    var campusid: String? = null,
    var campus: String? = null,
    var level: String? = null,
    var weekday: String? = null,
    var weeks: String? = null,
    var hour: String? = null,
    var minute: String? = null,
    var hmin: String? = null,
    var coursetime: String? = null,
    var levelname: String? = null,
    var classname: String? = null,
    var schoolid: String? = null,
    var schoolname: String? = null,
    var muser_id: String? = null,
    var cuser_id: String? = null,
    var issingle: String? = null,
    var createtime: String? = null,
    var updatetime: String? = null,
    var has_sickleave: String? = null,
    var has_thingleave: String? = null,
    var has_courselate: String? = null,
    var has_truant: String? = null,
    var has_morninglate: String? = null,
    var attlists: ArrayList<AttendanceStuBean>?,
    var tags: ArrayList<String>? = null,
    var showhim: String? = null

) : Parcelable