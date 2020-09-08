package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.model.GroupBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TimetableResponse(
    var list : MutableList<TimetableBean>,
    var classs : MutableList<ClassBean>,
    var groups : MutableList<GroupBean>,
    var positions : MutableList<TimetableLabelBean>
)