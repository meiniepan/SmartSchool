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
data class SiteBean(
    var date: String = "",
    var position: Int = -1,
    var list:ArrayList<SiteItemBean> = ArrayList()

) : Parcelable