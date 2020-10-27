package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/10/27
 * Time: 17:06
 */
@Parcelize
data class DeviceBean(
    var name: String? = null,
    var isCheck: Boolean= false


) : Parcelable