package com.xiaoneng.ss.module.school.model

import com.xiaoneng.ss.model.ValueBean

/**
 * @author Burning
 * @description:工资列表
 * @date :2020/10/23 2:12 PM
 */
data class SalaryListBean(
    var id: String? = null,
    var time: String? = null,//发布日期
    var name: String? = null,//工资条名称
    var expend: ArrayList<ValueBean>? = null//扩展数组
)