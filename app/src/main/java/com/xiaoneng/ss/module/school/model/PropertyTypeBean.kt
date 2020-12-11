package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/11
 * Time: 17:06
 */
@Parcelize
data class PropertyTypeBean(
    var id: String?,
    var name: String?,
    var icon: String?,
    var explain: String?,
    var companyid: String?,
    var schoolid: String?,
    var schoolname: String?,
    var muser_id: String?,
    var cuser_id: String?,
    var createtime: String?,
    var updatetime: String?
) : Parcelable