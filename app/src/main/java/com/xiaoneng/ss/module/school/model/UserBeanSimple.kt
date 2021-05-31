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
    var realname: String ? = null,
    var classid: String ? = null,
    var usertype: String ? = null,
    var dep_name: String ? = null,
    var classmaster_classname: String ? = null,
    var deps: ArrayList<UserBeanSimple> ? = null,
    var phone: String ? = null,
    var cno: String ? = null,
    var topdepartid: String ? = null,
    var secdepartid: String ? = null


    ):Parcelable