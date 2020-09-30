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
    var token: String? = null,
    var uid: String? = null,
    var icon: String? = null,
    var cno: String? = null,//学号
    var sno: String? = null,//教育局编号
    var realname: String? = null,
    var sex: String? = null,//性别0未知1男2女
    var phone: String? = null,
    var birthday: String? = null,
    var portrait: String? = null,
    var class_id: String? = null,
    var schoolid: String? = null,
    var classname: String? = null,
    var companyid: String? = null,
    var password: String? = null,
    var openid: String? = null,
    var wxname: String? = null,
    var remark: String? = null,
    var isactive: String? = null,
    var device_no: String? = null,
    var parentphone: String? = null,
    var parentuid: String? = null,
    var parentname: String? = null,
    var classmaster: String? = null,//是否班主任
    var isad: String? = null,//学生是否是考勤员
    var roleid: String? = null,
    var usertype: String? = null,//学生1  老师2  家长3
    var logintype: String? = null,//学生self   家长parents
    var parents: ArrayList<ParentBean>? = null,
    var students: ArrayList<StudentBean>? = null,
    var domain: String? = null
)