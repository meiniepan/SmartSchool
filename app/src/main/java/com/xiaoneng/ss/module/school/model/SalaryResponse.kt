package com.xiaoneng.ss.module.school.model

/**
 * @author Burning
 * @description:工资
 * @date :2020/11/12 2:12 PM
 */
data class SalaryResponse(
    var data: ArrayList<SalaryListBean>? = null,
    var total: String? = null,
    var page: String? = null

)