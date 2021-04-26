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
data class DepartmentPersonBean(
    var id: String? = "",
    var departmentsname: String? = "",
    var isAll: Boolean = false,
    var data: MutableList<StudentBean> = ArrayList()


) : Parcelable