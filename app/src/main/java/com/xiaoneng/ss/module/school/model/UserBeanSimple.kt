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
data class UserBeanSimple(
    var uid: String ? = null,
    var name: String ? = null,
    var usertype: String ? = null,
    var topdepartid: String ? = null,
    var secdepartid: String ? = null


    ):Parcelable