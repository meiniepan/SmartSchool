package com.xiaoneng.ss.module.circular.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class DayBean(
    var day: Long? = 0L,
    var dayOfSun: String? = "",
    var dayOfLunar: String? = "",
    var isCheck: Boolean = false,
    var inMonth: Boolean = false,
    var eventList: MutableList<ScheduleBean> = ArrayList()

    )
