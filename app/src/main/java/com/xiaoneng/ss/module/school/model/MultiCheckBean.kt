package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import com.xiaoneng.ss.model.StudentBean
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
@Parcelize
data class MultiCheckBean(
    var id: String? = "",
    var name: String? = "",
    var isChecked: Boolean = false,
    var canCheck: Boolean = true
) : Parcelable