package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TaskBean(
    var token: String,
    var taskname: String,
    var plantime: String,
    var plantotal: String,
    var involve: MutableList<UserBeanSimple> = ArrayList(),
    var id: String = "",
    var taskid: String = "",
    var uid: String = "",
    var usertype: String = "",
    var fileinfo: String = "",
    var feedback: String = "",
    var examine: String = "",
    var examinestatus: String = "",
    var tasktime: String = "",
    var remark: String = "",
    var type: String = "",
    var status: String = "",
    var schoolname: String = "",
    var schoolid: String = "",
    var operatorname: String = "",
    var operatortype: String = "",
    var operatorid: String = ""
)