package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class TaskBean(
    var token: String? = null,
    var taskname: String? = null,
    var plantime: String? = null,
    var plantotal: String? = null,
    var involve: String? = null,
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
    var schoolname: String? = null,
    var schoolid: String? = null,
    var operatorname: String? = null,
    var operatortype: String? = null,
    var operatorid: String? = null,
    var sendlabel: String? = null//与involve二选一，all teacher students classmaster以逗号隔开
)