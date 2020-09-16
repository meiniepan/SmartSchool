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
data class TaskLogRequest(
    var token: String,
    var id: String,
    var feedback: String? = null,//任务反馈
    var fileinfo: String? = null,
    var examine: String? = null,
    var examinestatus: String? = null,
    var status: String? = null
) : Parcelable