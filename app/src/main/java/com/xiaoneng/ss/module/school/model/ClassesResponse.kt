package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.ClassBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class ClassesResponse(
    var list : MutableList<ClassBean>,
    var level : String,
    var levelname : String,
    var lastid : String
)