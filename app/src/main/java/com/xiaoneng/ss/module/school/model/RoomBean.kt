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
data class RoomBean(
    var id: String?,
    var type: String?,
    var classroomname: String?,
    var building: String?,
    var floor: String?,
    var addr: String?,
    var campus: String?,
    var campusid: String?,
    var total: String?,
    var remark: String?,
    var status: String?,
    var isorder: String?,
    var ostime: String?,
    var oetime: String?,
    var schoolname: String?,
    var schoolid: String?,
    var muser_id: String?,
    var cuser_id: String?


    ) : Parcelable