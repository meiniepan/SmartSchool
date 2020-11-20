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
data class FileInfoBean(
    var name: String ? = null,
    var type: String ? = null,
    var url: String ? = null,
    var ext: String ? = null
    ):Parcelable