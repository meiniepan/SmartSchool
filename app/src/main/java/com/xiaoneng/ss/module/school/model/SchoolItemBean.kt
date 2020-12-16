package com.xiaoneng.ss.module.school.model

import android.view.View

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/16
 * Time: 17:06
 */
data class SchoolItemBean(
    var name: String = "",
    var remark: String = "",
    var icon: Int = -1,
    var click: View.OnClickListener? = null
)