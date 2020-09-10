package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.model.GroupBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class AttendanceResponse(
    var data : ArrayList<AttendanceBean> = ArrayList(),
    var attendances : ArrayList<AttendanceStuBean> = ArrayList(),
    var classs : ArrayList<ClassBean> = ArrayList(),
    var groups : ArrayList<GroupBean> = ArrayList(),
    var lastid : String,
    var day : String,
    var total : String,
    var semesters : String
)