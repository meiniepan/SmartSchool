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
data class FolderModifyBean(
    var token: String? = null,
    var id: String? = null,
    var foldername: String? = null,
    var fileinfo: String? = null,
    var filename: String ? = null,
) : Parcelable