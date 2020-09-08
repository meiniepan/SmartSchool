package com.xiaoneng.ss.module.circular.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class ScheduleBean(
    var token: String? = "",
    var id: String? = "",
    var realname: String?= "",
    var type: String?= "",
    var title: String?= "",
    var remark: String?= "",
    var schoolname: String?= "",
    var status: String?= "",
    var usertype: String?= "",
    var uid: String?= "",
    var scheduletime: String?= "",
    var scheduleover: String?= "",
    var day: String?= "",
    var month: String?= "",
    var him: String?= "",
    var cno: String?= "",
    var color: String?= "",
    var schoolid: String?= "",
    var muser_id: String?= "",
    var cuser_id: String?= ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
        parcel.writeString(id)
        parcel.writeString(realname)
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeString(remark)
        parcel.writeString(schoolname)
        parcel.writeString(status)
        parcel.writeString(usertype)
        parcel.writeString(uid)
        parcel.writeString(scheduletime)
        parcel.writeString(scheduleover)
        parcel.writeString(day)
        parcel.writeString(month)
        parcel.writeString(him)
        parcel.writeString(cno)
        parcel.writeString(color)
        parcel.writeString(schoolid)
        parcel.writeString(muser_id)
        parcel.writeString(cuser_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleBean> {
        override fun createFromParcel(parcel: Parcel): ScheduleBean {
            return ScheduleBean(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleBean?> {
            return arrayOfNulls(size)
        }
    }

}