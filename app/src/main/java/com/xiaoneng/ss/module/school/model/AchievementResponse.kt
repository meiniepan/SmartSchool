package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class AchievementResponse(
    var list: MutableList<AchievementBean>?,
    var totle: String?,
    var course: MutableList<CourseBean>?,
    var classs: String?,
    var groups: String?,
    var lastid: String?,
    var day: String?,
    var semesters: String?
)