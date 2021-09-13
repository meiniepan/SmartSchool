package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import com.xiaoneng.ss.model.ClassBean
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/24
 * Time: 17:06
 */
@Parcelize
data class QuantizeTemplateSetting(
    var active: Boolean?=null,
    var max: Int?=null,
    var min: Int?=null,
    var step: Int?=null,
    var stime: String?=null,

) : Parcelable