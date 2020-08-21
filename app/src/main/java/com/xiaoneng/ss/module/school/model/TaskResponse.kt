package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class TaskResponse(
    var data : MutableList<TaskBean>,
    var totle : String,
    var complete : String,
    var lastid : String
)