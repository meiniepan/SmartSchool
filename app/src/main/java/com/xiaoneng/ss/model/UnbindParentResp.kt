package com.xiaoneng.ss.model

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
data class UnbindParentResp(
    var total: String? = null,
    var parents: ArrayList<ParentBean>?

):Parcelable