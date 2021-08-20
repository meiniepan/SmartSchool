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
data class NoticeExpandBean(
    var id: String?,
    var action: String? = ""
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
