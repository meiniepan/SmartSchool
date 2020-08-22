package com.xiaoneng.ss.module.circular.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class NoticeBean(
    var id: String?,
    var noticeid: String? = "",
    var taskname: String?= "",
    var uid: String?= "",
    var usertype: String?= "",
    var taskinfo: String?= "",
    var status: String?= "",
    var received: String?= "",
    var title: String?= "",
    var noticetime: String?= "",
    var remark: String?= "",
    var type: String?= "",
    var schoolname: String?= "",
    var schoolid: String?= "",
    var operatorname: String?= ""
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
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(noticeid)
        parcel.writeString(taskname)
        parcel.writeString(uid)
        parcel.writeString(usertype)
        parcel.writeString(taskinfo)
        parcel.writeString(status)
        parcel.writeString(received)
        parcel.writeString(title)
        parcel.writeString(noticetime)
        parcel.writeString(remark)
        parcel.writeString(type)
        parcel.writeString(schoolname)
        parcel.writeString(schoolid)
        parcel.writeString(operatorname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoticeBean> {
        override fun createFromParcel(parcel: Parcel): NoticeBean {
            return NoticeBean(parcel)
        }

        override fun newArray(size: Int): Array<NoticeBean?> {
            return arrayOfNulls(size)
        }
    }
}