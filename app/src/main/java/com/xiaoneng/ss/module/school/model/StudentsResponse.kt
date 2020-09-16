package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.StudentBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class StudentsResponse(
    var data : MutableList<StudentBean>,
    var lastid : String
)