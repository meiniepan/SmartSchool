package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/04/28
 * Time: 17:06
 */
@Parcelize
data class UnreadMemberBean(
    var uid: String ? = null,
    var noticeid: String ? = null,
    var username: String ? = null,
    var usertype: String ? = null,

    var secdepartid: String ? = null


    ):Parcelable