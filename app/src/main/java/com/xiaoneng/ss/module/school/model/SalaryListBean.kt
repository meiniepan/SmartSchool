package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Burning
 * @description:工资列表
 * @date :2020/10/23 2:12 PM
 */
@Parcelize
data class SalaryListBean(
    var id: String? = null,
    var createtime: String? = null,//发布日期
    var title: String? = null,//工资条名称
    var fileinfo: String? = null,//工资条名称
    var status: String? = null,//工资条名称
    var reachnum: String? = null,//工资条名称
    var total: String? = null,//工资条名称
    var reachwages: String? = null,//工资条名称
    var receivable: String? = null,//工资条名称
    var read: String? = null,//已读
    var remark: String? = null,//工资条名称
    var schoolid: String? = null,//工资条名称
    var schoolname: String? = null
//    var expand: ArrayList<ValueBean>? = null//扩展数组
) : Parcelable