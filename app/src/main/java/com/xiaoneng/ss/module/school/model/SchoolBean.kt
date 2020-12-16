package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/16
 * Time: 17:06
 */
data class SchoolBean(
    var name: String? = null,
    var items: ArrayList<SchoolItemBean> = ArrayList()
)