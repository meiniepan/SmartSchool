package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/24
 * Time: 17:06
 */
@Parcelize
data class QuantizeTypeBean(
    var id: String?,
    var typename: String?,
    var template: String?,
    var remark: String?,
    var operatorid: String?,
    var realname: String?,
    var schoolname: String?,
    var createtime: String?,
    var updatetime: String?,
    var checked: Boolean = false
) : Parcelable