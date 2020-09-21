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
data class StudentBean(
    var uid: String = "",
    var topdepartid: String ? = null,
    var secdepartid: String ? = null,
    var sno: String = "",
    var parentId: String = "",
    var realname: String = "",
    var sex: String = "",
    var phone: String = "",
    var birthday: String = "",
    var portrait: String = "",
    var classid: String = "",
    var level: String = "",
    var levelname: String = "",
    var classname: String = "",
    var schoolid: String = "",
    var companyid: String = "",
    var openid: String = "",
    var wxname: String = "",
    var remark: String = "",
    var isactive: String = "",
    var device_no: String = "",
    var cno: String = "",
    var usertype: String = "",
    var choice: String = "0",
    var roleid: String = "",
    var parents: MutableList<ParentBean> = ArrayList()
):Parcelable