package com.xiaoneng.ss.module.school.model

/**
 * @author Burning
 * @description:工资详情
 * @date :2020/10/23 2:12 PM
 */
data class SalaryExpandBean(
    var keys: ArrayList<String>? = null,
    var vals: ArrayList<String>? = null,
    var users: SalaryUserBean? = null
)
