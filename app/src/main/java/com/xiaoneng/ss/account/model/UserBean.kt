package com.xiaoneng.ss.account.model

import com.xiaoneng.ss.model.ParentBean
import com.xiaoneng.ss.model.StudentBean

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:54
 */
data class UserBean(
    var token: String,
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
    var parentphone: String= "",
    var parentuid: String= "",
    var parentname: String= "",
    var classmaster: String= "",
    var roleid: String= "",
    var usertype: String= "",//学生1  老师2  家长3
    var logintype: String= "",//学生self   家长parents
    var parents: ArrayList<ParentBean>? = null,
    var students: ArrayList<StudentBean>? = null,
    var domain: String= ""
)