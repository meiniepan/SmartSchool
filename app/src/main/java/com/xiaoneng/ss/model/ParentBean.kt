package com.xiaoneng.ss.model

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
data class ParentBean(
    var id: String?,
    var schoolid: String?,
    var phone: String?,
    var sid: String?,
    var createtime: String?
):Parcelable