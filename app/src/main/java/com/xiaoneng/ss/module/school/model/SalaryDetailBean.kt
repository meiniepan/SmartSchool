package com.xiaoneng.ss.module.school.model

/**
 * @author Burning
 * @description:工资详情
 * @date :2020/10/23 2:12 PM
 */
data class SalaryDetailBean(
    var id: String? = null,
    var uid: String? = null,
    var userinfo: SalaryUserBean? = null,
    var createtime: String? = null,
    var realname: String? = null,
    var reachwages: String? = null,
    var receivable: String? = null,
    var taxablewages: String? = null,
    var post: String? = null,
    var idcard: String? = null,
    var schoolid: String? = null,
    var schoolname: String? = null,
    var expand: SalaryExpandBean? = null//扩展数组
)
