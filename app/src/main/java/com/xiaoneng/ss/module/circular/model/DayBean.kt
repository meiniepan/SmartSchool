package com.xiaoneng.ss.module.circular.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class DayBean(
    var dayOfSun: String? = "",
    var dayOfLunar: String?= "",
    var isCheck: Boolean= false,
    var inMonth: Boolean= false

)