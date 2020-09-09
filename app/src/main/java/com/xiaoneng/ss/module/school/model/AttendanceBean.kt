package com.xiaoneng.ss.module.school.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class AttendanceBean(
    var id: String? = "",
    var cno: String? = "",
    var coursename: String? = "",
    var realname: String? = "",
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
    var campus: String? = "",
    var level: String? = "",
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
    var has_sickleave: String? = "",
    var has_thingleave: String? = "",
    var has_courselate: String? = "",
    var has_truant: String? = "",
    var has_morninglate: String? = "",
    var attlists: MutableList<AttendanceStuBean>?,
    var tags: MutableList<String>? =ArrayList(),
    var showhim: String? = ""

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("attlists"),
        TODO("tags"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(cno)
        parcel.writeString(coursename)
        parcel.writeString(realname)
        parcel.writeString(type)
        parcel.writeString(total)
        parcel.writeString(remark)
        parcel.writeString(classroomid)
        parcel.writeString(classroomname)
        parcel.writeString(teacheruid)
        parcel.writeString(teachername)
        parcel.writeString(semesterid)
        parcel.writeString(classid)
        parcel.writeString(groupid)
        parcel.writeString(semester)
        parcel.writeString(campusid)
        parcel.writeString(campus)
        parcel.writeString(level)
        parcel.writeString(weekday)
        parcel.writeString(weeks)
        parcel.writeString(hour)
        parcel.writeString(minute)
        parcel.writeString(hmin)
        parcel.writeString(coursetime)
        parcel.writeString(schoolid)
        parcel.writeString(schoolname)
        parcel.writeString(muser_id)
        parcel.writeString(cuser_id)
        parcel.writeString(issingle)
        parcel.writeString(createtime)
        parcel.writeString(updatetime)
        parcel.writeString(has_sickleave)
        parcel.writeString(has_thingleave)
        parcel.writeString(has_courselate)
        parcel.writeString(has_truant)
        parcel.writeString(has_morninglate)
        parcel.writeString(showhim)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttendanceBean> {
        override fun createFromParcel(parcel: Parcel): AttendanceBean {
            return AttendanceBean(parcel)
        }

        override fun newArray(size: Int): Array<AttendanceBean?> {
            return arrayOfNulls(size)
        }
    }
}