package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import com.xiaoneng.ss.model.StudentBean
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
@Parcelize
data class DepartmentBean(
    var id: String? = "",
    var name: String? = "",
    var list:ArrayList<StudentBean> = ArrayList(),
    var num: String? = "0"


) : Parcelable