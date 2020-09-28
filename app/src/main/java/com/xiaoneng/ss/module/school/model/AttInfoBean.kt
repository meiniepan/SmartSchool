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
data class AttInfoBean(
    var id: String? = null,
    var realname: String? = null,
    var uid: String? = null,
    var usertype: String? = null,
    var type: String? = null,
    var status: String? = null,
    var remark: String? = null,
    var classid: String? = null,
    var classname: String? = null,
    var sectionname: String? = null,
    var levelname: String? = null,
    var groupid: String? = null,
    var atttime: String? = null,
    var teachername: String? = null,
    var teacheruid: String? = null,
    var attendances: String? = null,
    var courseid: String? = null,
    var crsid: String? = null,
    var leavediseasename: String? = null,
    var leaveremark: String? = null,
    var coursesname: String? = null,
    var courses_teachername: String? = null,
    var courses_teacheruid: String? = null,
    var leavetype: String? = null,
    var leaveid: String? = null,
    var leave: LeaveBean? = null,
    var stleave: String? = null,
    var etleave: String? = null,
    var schoolid: String? = null,
    var schoolname: String? = null,
    var muser_id: String? = null,
    var cuser_id: String? = null,
    var createtime: String? = null,
    var updatetime: String? = null,
    var courseslist: MutableList<AttCourseBean>? = null

) : Parcelable