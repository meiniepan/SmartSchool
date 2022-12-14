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
data class SiteResp(
    var data: ArrayList<SiteBean>?,
    var days: ArrayList<String>?,
    var semesters: String?
    ) : Parcelable