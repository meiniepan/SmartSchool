package com.xiaoneng.ss.module.school.model

import android.os.Parcel
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
data class DiskFileBean(
    var token: String? = "",
    var id: String = "",
    var parentid: String = "",
    var foldername: String = "",
    var updatetime: String = "",
    var filename: String = "",
    var fileinfo: String = "",
    var objectid: String = "",
    var folderid: String? = "",
    var path: String = "",
    var objectKey: String = "",
    var totalSize: Long = 0,
    var currentSize: Long = 0,
    var progress: Long = 0,
    var status: Int = 0,//0正在下载 1暂停  2完成
    @Transient  var task: OSSAsyncTask<*>? = null,
    var schoolname: String= ""
)