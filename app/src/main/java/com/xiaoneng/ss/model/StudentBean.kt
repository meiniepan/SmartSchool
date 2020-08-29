package com.xiaoneng.ss.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class StudentBean(
    var uid: String,
    var sno: String,
    var realname: String,
    var sex: String,
    var phone: String,
    var birthday: String,
    var portrait: String,
    var classid: String,
    var level: String,
    var levelname: String,
    var schoolid: String,
    var classname: String,
    var companyid: String,
    var openid: String,
    var wxname: String,
    var remark: String,
    var isactive: String,
    var device_no: String,
    var cno: String,
    var usertype: String,
    var roleid: String,
    var parents: MutableList<ParentBean>
)