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
data class LeaveBean(
    var token: String,
    var uid: String,
    var remark: String ,
    var atttime: String,
    var status: String = "3",//状态0迟到1已完成2旷课3请假
    var type: String = "3",//考勤类型1早考勤2早到班3课堂考勤
    var crsid: String,//课程ID
    var usertype: String = "",
    var stleave: String = "",
    var etleave: String = "",
    var teacheruid: String = "",
    var title: String = "",
    var leavetype: String = "1",//请假类型1事假2病假3传染病
    var isfever: String = "0",//发热
    var isdiarrhea: String = "0",//腹泻
    var isvomit: String = "0",//呕吐
    var ismedical: String = "0",//就医
    var diseasename: String = "",//病名
    var fileinfo: String = "",
    var timelen: String = "",
    var temperature: String = "",//温度
    var attlists: MutableList<LeaveCourseBean> = ArrayList(),
    var hospital: String = ""//医院
) : Parcelable