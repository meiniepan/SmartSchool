package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.ValueBean

/**
 * @author Burning
 * @description:工资详情
 * @date :2020/10/23 2:12 PM
 */
data class SalaryDetailBean(
    var id: String? = null,
    var teacher_name: String? = null,
    var id_number: String? = null,//身份证号码
    var plan_time: String? = null,//计划发放时间
    var operate_time: String? = null,//实际发放时间
    var actual_paid: String? = null,//实际支付合计
    var expend: ArrayList<ValueBean>? = null//扩展数组
)
