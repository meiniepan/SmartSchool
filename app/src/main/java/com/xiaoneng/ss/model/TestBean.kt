package com.xiaoneng.ss.model

import com.xiaoneng.ss.module.school.model.CourseBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TestBean(
    var testname: String,
    var data: MutableList<CourseBean>

)