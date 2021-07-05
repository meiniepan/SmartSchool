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
    var id: String? = null,
    var typename: String? = null,
    var template: String? = null,
    var remark: String? = null,
    var operatorid: String? = null,
    var realname: String? = null,
    var schoolname: String? = null,
    var createtime: String? = null,
    var updatetime: String? = null,
    var checked: Boolean = false
) : Parcelable