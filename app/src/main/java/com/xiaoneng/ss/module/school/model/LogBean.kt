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
data class LogBean(
    var id: String? = "",
    var taskid: String? = "",
    var taskname: String? = "",
    var uid: String? = "",
    var usertype: String? = "",
    var username: String? = "",
    var fileinfo: String? = "",
    var feedback: String? = "",
    var examine: String? = "",
    var examinestatus: String? = "",
    var plantime: String? = "",
    var status: String? = "",
    var taskstatus: String? = "",
    var classroomname: String? = ""



    ):Parcelable