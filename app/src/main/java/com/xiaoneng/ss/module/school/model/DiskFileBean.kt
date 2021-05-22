package com.xiaoneng.ss.module.school.model

import com.alibaba.sdk.android.oss.internal.OSSAsyncTask

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
    var downTaskId: Long = 0,
    var totalSize: Long = 0,
    var currentSize: Long = 0,
    var progress: Long = 0,
    var isChecked: Boolean = false,
    var status: Int = -1,//0正在下载 1暂停  2完成
    @Transient var task: OSSAsyncTask<*>? = null,
    var schoolname: String = ""
)