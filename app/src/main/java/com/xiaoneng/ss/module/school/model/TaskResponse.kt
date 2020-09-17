package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TaskResponse(
    var data : MutableList<TaskDetailBean>,
    var totle : String,
    var complete : String,
    var lastid : String
)