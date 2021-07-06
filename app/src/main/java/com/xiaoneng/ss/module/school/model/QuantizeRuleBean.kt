package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import com.xiaoneng.ss.model.ClassBean
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/24
 * Time: 17:06
 */
@Parcelize
data class QuantizeRuleBean(
    var required: QuantizeRuleRequiredBean?=null,

) : Parcelable