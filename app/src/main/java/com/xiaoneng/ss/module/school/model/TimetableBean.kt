package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TimetableBean(
    var date: String = "",
    var time: String= "",
    var week: String= "",
    var list:ArrayList<CourseBean> = ArrayList()

)