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
data class NoticeBean(
    var id: String?,
    var noticeid: String? = "",
    var taskname: String?= "",
    var uid: String?= "",
    var usertype: String?= "",
    var taskinfo: String?= "",
    var fileinfo: String?= "",
    var status: String?= "",
    var received: String?= "",
    var title: String?= "",
    var noticetime: String?= "",
    var remark: String?= "",
    var type: String?= "",
    var send_uid: String?= "",
    var send_username: String?= "",
    var schoolname: String?= "",
    var schoolid: String?= "",
//    var action: String?= "",//1同步新任务 2任务日志被驳回或通过 3考勤更新 4时令更替 5成绩更新 6发布新版本
    var expand: NoticeExpandBean?= null,//1任务协作 2日程安排 3学生考勤 4成绩汇总 5报修报送 6发布新版本
    var operatorname: String?= ""
):Parcelable
//"admin/spacebook/default":场地预约
//"admin/notices/default":通知公告
//"admin/schedules/default":日程安排
//"moral/moral/default":量化评比
//"admin/wages/default":工资条
//"admin/repair/default":报修报送
//"admin/tasks/default":任务协作
//"disk/folder/default":教学云盘
//"admin/attendances/default":学生考勤
//"admin/achievements/default":成绩汇总
//"admin/courses/default":我的课表
