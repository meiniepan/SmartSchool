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
    var id: String? = null,
    var overtime: String? = null,
    var taskid: String? = null,
    var uid: String? = null,
    var usertype: String? = null,
    var fileinfo: String? = null,
    var feedback: String? = null,
    var examine: String? = null,
    var examinestatus: String? = null,
    var tasktime: String? = null,
    var remark: String? = null,
    var ordertime: String? = null,
    var type: String? = null,
    var status: String? = null,//0待发布(草稿)1进行中2完成3关闭
    var taskstatus: String? = null,//0待发布(草稿)1进行中2完成3关闭
    var completestatus: String? = null,//0未完成1已完成
    var schoolname: String? = null,
    var schoolid: String? = null,
    var operatorname: String? = null,
    var operatortype: String? = null,
    var createtime: String? = null,
    var updatetime: String? = null,
    var tasklist: MutableList<LogBean> = ArrayList(),
    var operatorid: String? = null
)