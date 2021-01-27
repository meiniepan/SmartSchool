package com.xiaoneng.ss.model

import com.xiaoneng.ss.module.school.model.LevelCourseBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TestCourseResp(
    var testcourse: MutableList<TestBean>,
    var testname: MutableList<String>,
    var course: MutableList<LevelCourseBean>,
    var classs: MutableList<ClassBean>,
    var currenttestname: String,
    var total: String
)