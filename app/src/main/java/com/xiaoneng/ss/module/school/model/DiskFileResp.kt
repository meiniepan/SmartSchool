package com.xiaoneng.ss.module.school.model

import android.os.Parcel
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask
import com.alibaba.sdk.android.oss.model.OSSResult
import com.tencent.bugly.proguard.T

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class DiskFileResp(
    var data: ArrayList<DiskFileBean>? = null

)