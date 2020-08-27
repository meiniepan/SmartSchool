package com.xiaoneng.ss.common.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.internal.Intrinsics

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @CreateDate: 2020/4/5 20:24
 */
object DateUtil {
    /**
     * 获取当前时刻
     *
     * @return
     */
    fun getNowTime(): Date? {
        return formatDate("yyyy-MM-dd", Date(Date().time))
    }

    /**
     * 日期格式化
     *
     * @param formatStyle
     * @param date
     * @return
     */
    fun formatDate(formatStyle: String, date: Date?): Date? {
        Intrinsics.checkParameterIsNotNull(formatStyle, "formatStyle")
        return if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            val formatDate = sdf.format(date)
            try {
                val var10000 = sdf.parse(formatDate)
                Intrinsics.checkExpressionValueIsNotNull(var10000, "sdf.parse(formatDate)")
                var10000
            } catch (var6: ParseException) {
                var6.printStackTrace()
                Date()
            }
        } else {
            Date()
        }
    }

    /**
     * 日期格式化
     *
     * @param formatStyle
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun getWhichMonth(date: Long = Date().time): String {
        val sdf = SimpleDateFormat("yyyy年MM月")
        return sdf.format(Date(date))

    }

    fun isSameDay(date: Long = Date().time): Boolean {
       var cal1 :Calendar  = Calendar.getInstance()
       var cal2 :Calendar  = Calendar.getInstance()
        var date1 = Date(System.currentTimeMillis())
        var date2 = Date(date)
        cal1.time= date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)

    }
}