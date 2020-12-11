package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
@Parcelize
data class SiteItemBean(
    var id: String? = null,
    var roomid: String? = null,
    var ostime: String? = null,
    var oetime: String? = null,
    var os_position: String? = null,
    var oe_position: String? = null,
    var operatorid: String? = null,
    var operator: UserBeanSimple? = null,
    var remark: String? = null,
    var isBooked: Boolean = true,//是否被预订
    var isChecked: Boolean = false,//是否被选中
    var timeStr: String? = null


    ):Parcelable