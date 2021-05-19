package com.xiaoneng.ss.module.school.model

import android.os.Parcel
import android.os.Parcelable
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask
import com.alibaba.sdk.android.oss.model.OSSResult
import com.tencent.bugly.proguard.T
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
@Parcelize
data class FolderBean(
    var token: String? = null,
    var id: String = "",
    var parentid: String = "",
    var foldername: String = "",
    var fileinfo: String = "",
    var objectid: String = "",
    var folderid: String = "",
    var filename: String = "",
    var fullName: String = "",
    var updatetime: String = "",
    var path: String = "",
    var schoolname: String= "",
    var cuser_id: String= "",
    var cuser_realnem: String= "",
    var isFolder:Boolean = true,
    var isChecked:Boolean = false,
    var isPrivate:Boolean = true
) : Parcelable