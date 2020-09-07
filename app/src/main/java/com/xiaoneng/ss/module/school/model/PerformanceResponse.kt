package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.CourseBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class PerformanceResponse(
    var list : MutableList<PerformanceBean>,
    var totle : String,
    var course : MutableList<CourseBean>,
    var classs : String,
    var groups : String,
    var lastid : String,
    var day : String,
    var semesters : String
)