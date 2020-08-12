package com.xiaoneng.ss.network

import org.jetbrains.annotations.NotNull

/**
 * @author Burning
 * @description:
 * @date :2020/8/11 4:16 PM
 */
data class StudentInfoReq(
    @NotNull var token: String,
//    var sno: String = "",//教委学籍号
    var realname: String= ""
//    var portrait: String= "",
//    var cno: String= "",//学校自定义学号
//    var sex: String= "",//性别0未知1男2女
//    var birthday: String= "",
//    var classid: String= "",
//    var classname: String= "",
//    var schoolid: String= "",
//    var companyid: String= "",
//    var openid: String= "",//微信openid
//    var wxname: String= "",//微信昵称
//    var remark: String= "",
//    var isactive: String= "",//是否已激活1激活0未激活
//    var device_no: String= "",
//    var roleid: String= ""//权限组id
)