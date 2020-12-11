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
    var id: String?,
    var roomid: String?,
    var classroomname: String?,
    var classroom: RoomBean?,
    var status: String?,//-1已取消 0未开始 1进行中 2已结束 3被占用
    var addr: String?,
    var total: String?,
    var operatorid: String?,
    var operator: UserBeanSimple?,
    var remark: String? = null,
    var book_position: String? = null,
    var ostime: String? = null,
    var oetime: String? = null,
    var os_position: String? = null,
    var oe_position: String? = null,
    var books:ArrayList<SiteItemBean>? = ArrayList(),
    var position: Int = -1,
    var chosenDay: Long = 0,
    var startType: Int = 0//0灰色进入  1白色可选进入

    ) : Parcelable