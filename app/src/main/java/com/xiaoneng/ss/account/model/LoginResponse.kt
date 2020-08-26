package com.xiaoneng.ss.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:54
 */
data class LoginResponse(
    var icon: String = "",
    var uid: String= "",
    var sno: String= "",
    var realname: String= "",
    var sex: String= "",
    var phone: String= "",
    var birthday: String= "",
    var portrait: String= "",
    var class_id: String= "",
    var schoolid: String= "",
    var classname: String= "",
    var companyid: String= "",
    var openid: String= "",
    var wxname: String= "",
    var remark: String= "",
    var isactive: String= "",
    var device_no: String= "",
    var cno: String= "",
    var roleid: String= "",
    var usertype: String= "",//学生1  老师2  家长3
    var parents: ArrayList<ParentBean> = ArrayList(),
    var token: String= "",
    var domain: String= ""
)