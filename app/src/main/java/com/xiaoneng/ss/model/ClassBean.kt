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
data class ClassBean(
    var classid: String?,
    var id: String?,
    var classname: String?,
    var levelclass: String?,
    var level: String?,
    var choice: String?,
    var levelname: String?
): Parcelable