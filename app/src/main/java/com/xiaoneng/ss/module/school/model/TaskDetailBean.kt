package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TaskDetailBean(
    var token: String,
    var taskname: String,
    var plantime: String,
    var plantotal: String,
    var involve: MutableList<UserBeanSimple>,
    var id: String = "",
    var overtime: String = "",
    var taskid: String = "",
    var uid: String = "",
    var usertype: String = "",
    var fileinfo: String = "",
    var feedback: String = "",
    var examine: String = "",
    var examinestatus: String = "",
    var tasktime: String = "",
    var remark: String = "",
    var ordertime: String = "",
    var type: String = "",
    var status: String = "",//0待发布(草稿)1进行中2完成3关闭
    var completestatus: String = "",//0未完成1已完成
    var schoolname: String = "",
    var schoolid: String = "",
    var operatorname: String = "",
    var operatortype: String = "",
    var createtime: String = "",
    var updatetime: String = "",
    var tasklist: MutableList<LogBean> = ArrayList(),
    var operatorid: String = ""
)