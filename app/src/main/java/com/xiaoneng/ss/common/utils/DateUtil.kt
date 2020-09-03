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
    fun formatDateCustomDay(date: Long): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun getNearTimeBegin(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("MM")
        val s2 = SimpleDateFormat("dd")
        var s3 = SimpleDateFormat("HH")
        var s4 = SimpleDateFormat("mm")
        val MM = s1.format(Date(date))
        val dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "30"
        } else if (mm.toInt() > 30) {
            mm = "00"
            hh = (hh.toInt() + 1).toString()
        }
        return MM + "月" + dd + "日 " + hh + ":" + mm
    }

    @SuppressLint("SimpleDateFormat")
    fun getNearTimeBeginYear(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("yyyy-MM")
        val s2 = SimpleDateFormat("dd")
        var s3 = SimpleDateFormat("HH")
        var s4 = SimpleDateFormat("mm")
        val MM = s1.format(Date(date))
        val dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "30"
        } else if (mm.toInt() > 30) {
            mm = "00"
            hh = (hh.toInt() + 1).toString()
        }
        return "$MM-$dd $hh:$mm"
    }

    @SuppressLint("SimpleDateFormat")
    fun getNearTimeEnd(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("MM")
        val s2 = SimpleDateFormat("dd")
        var s3 = SimpleDateFormat("HH")
        var s4 = SimpleDateFormat("mm")
        val MM = s1.format(Date(date))
        val dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "00"
            hh = (hh.toInt() + 1).toString()
        } else if (mm.toInt() > 30) {
            mm = "30"
            hh = (hh.toInt() + 1).toString()
        }
        return MM + "月" + dd + "日 " + hh + ":" + mm
    }

    @SuppressLint("SimpleDateFormat")
    fun getNearTimeEndYear(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("yyyy-MM")
        val s2 = SimpleDateFormat("dd")
        var s3 = SimpleDateFormat("HH")
        var s4 = SimpleDateFormat("mm")
        val MM = s1.format(Date(date))
        val dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "00"
            hh = (hh.toInt() + 1).toString()
        } else if (mm.toInt() > 30) {
            mm = "30"
            hh = (hh.toInt() + 1).toString()
        }
        return MM + "-" + dd + " " + hh + ":" + mm
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomMonth(date: Long): String {
        val sdf = SimpleDateFormat("yyyyMM")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun getWhichMonth(date: Long = Date().time): String {
        val sdf = SimpleDateFormat("yyyy年MM月")
        return sdf.format(Date(date))

    }

    fun isSameDay(date: Long = Date().time): Boolean {
        var cal1: Calendar = Calendar.getInstance()
        var cal2: Calendar = Calendar.getInstance()
        var date1 = Date(System.currentTimeMillis())
        var date2 = Date(date)
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)

    }

    fun getWeek(day: Int): String? {
        return when (day) {
            1 -> "周日"
            2 -> "周一"
            3 -> "周二"
            4 -> "周三"
            5 -> "周四"
            6 -> "周五"
            7 -> "周六"
            else -> ""
        }
    }

    fun getWeekPosition(day: Int): Int {
        return when (day) {
            1 -> 6
            2 -> 0
            3 -> 1
            4 -> 2
            5 -> 3
            6 -> 4
            7 -> 5
            else -> 0
        }
    }

    fun getDateString(yy: String? = "",m1: String? = "",dd: String? = "",hh: String? = "",m2: String? = ""): String {
        return "${yy}-${m1}-${dd} $hh:$m2"
    }
}