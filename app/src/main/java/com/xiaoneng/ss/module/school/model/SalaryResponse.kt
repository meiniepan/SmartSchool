package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Burning
 * @description:工资
 * @date :2020/11/12 2:12 PM
 */
@Parcelize
data class SalaryResponse(
    var data: ArrayList<SalaryListBean>? = null,
    var total: String? = null,
    var page: String? = null

) : Parcelable