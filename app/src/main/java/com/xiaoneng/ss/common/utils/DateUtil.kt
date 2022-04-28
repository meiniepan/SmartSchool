package com.xiaoneng.ss.common.utils

import android.annotation.SuppressLint
import android.widget.TextView
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
@SuppressLint("SimpleDateFormat")
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
    fun formatDate(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun getTodayStr(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun formatTitleToday(date0: String = ""): String {
        var cal = Calendar.getInstance()
            var date = formatDateString(date0)
        if (date.length >= 8) {
            cal.set(Calendar.YEAR, date.substring(0, 4).toInt())
            cal.set(Calendar.MONTH, date.substring(4, 6).toInt() - 1)
            cal.set(Calendar.DAY_OF_MONTH, date.substring(6, 8).toInt())
        }
        val sdf = SimpleDateFormat("yyyy年MM月dd日 ")
        return sdf.format(cal.time) + getWeek(cal)

    }

    fun formatDateString(date0: String): String {
        var date = ""
        date = date0.replace("-", "")
        date = date.replace("/", "")
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun formatShowTime(date: String): String {
//        var result = ""
//        if (date.length > 10) {
//            if (isSameDay(date)) {
//                result = date.substring(
//                    11,
//                    date.length
//                )
//            } else if (isSameYear(date)) {
//                result = date.substring(
//                    5,
//                    date.length
//                )
//            } else {
//                result = date.substring(
//                    2,
//                    date.length
//                )
//            }
//
//        } else {
//            result = date
//        }
        return date

    }


    @SuppressLint("SimpleDateFormat")
    fun showTimeFromNet(date: String, textView1: TextView, textView2: TextView) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        if (date.length >= 15) {
            var time1 = date.substring(
                5, 7
            ) + "月" +
                    date.substring(
                        8, 10
                    ) + "日 " + getWeek(sdf.parse(date))
            var time2 = date.substring(
                date.length - 5,
                date.length
            )
            textView1.text = time1
            textView2.text = time2
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomDay(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyyMMdd")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomBookDay(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        return sdf.format(Date(date))

    }


    fun formatDateCustomWeekDay(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyy年MM月dd " + getWeek(Date(date)))
        return sdf.format(Date(date))

    }


    @SuppressLint("SimpleDateFormat")
    fun getBookSitePositionNearNow(date: Long = System.currentTimeMillis()): Int {
        val s1 = SimpleDateFormat("yyyy-MM")
        val s2 = SimpleDateFormat("dd")
        val s3 = SimpleDateFormat("HH")
        val s4 = SimpleDateFormat("mm")
        var MM = s1.format(Date(date))
        var dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "30"
        } else if (mm.toInt() > 30) {
            //23点后进行跨天处理
            if (hh.toInt() == 23) {
                var date2 = date + 30 * 60 * 1000
                MM = s1.format(Date(date2))
                dd = s2.format(Date(date2))
                hh = s3.format(Date(date2))
            } else {
                hh = (hh.toInt() + 1).toString()
            }
            mm = "00"

        }
        var tStr: String = "$hh:$mm"
        var list = initSiteTimes()
        var p = 0
        for (i in list.indices) {
            if (list[i].timeStr == tStr) {
                p = i
                return p
            }
        }

        return p
    }

    @SuppressLint("SimpleDateFormat")
    fun getNearTimeBeginYear(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("yyyy-MM")
        val s2 = SimpleDateFormat("dd")
        val s3 = SimpleDateFormat("HH")
        val s4 = SimpleDateFormat("mm")
        var MM = s1.format(Date(date))
        var dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            mm = "30"
        } else if (mm.toInt() > 30) {
            //23点后进行跨天处理
            if (hh.toInt() == 23) {
                var date2 = date + 30 * 60 * 1000
                MM = s1.format(Date(date2))
                dd = s2.format(Date(date2))
                hh = s3.format(Date(date2))
            } else {
                hh = (hh.toInt() + 1).toString()
            }
            mm = "00"

        }
        return "$MM-$dd $hh:$mm"
    }


    @SuppressLint("SimpleDateFormat")
    fun getNearTimeEndYear(date: Long = System.currentTimeMillis()): String {
        val s1 = SimpleDateFormat("yyyy-MM")
        val s2 = SimpleDateFormat("dd")
        val s3 = SimpleDateFormat("HH")
        val s4 = SimpleDateFormat("mm")
        var MM = s1.format(Date(date))
        var dd = s2.format(Date(date))
        var hh = s3.format(Date(date))
        var mm = s4.format(Date(date))
        if (mm.toInt() < 30) {
            if (hh.toInt() == 23) {
                var date2 = date + 30 * 60 * 1000
                MM = s1.format(Date(date2))
                dd = s2.format(Date(date2))
                hh = s3.format(Date(date2))
            } else {
                hh = (hh.toInt() + 1).toString()
            }
            mm = "00"
        } else if (mm.toInt() > 30) {
            if (hh.toInt() == 23) {
                var date2 = date + 30 * 60 * 1000
                MM = s1.format(Date(date2))
                dd = s2.format(Date(date2))
                hh = s3.format(Date(date2))
            } else {
                hh = (hh.toInt() + 1).toString()
            }
            mm = "30"
        }
        return "$MM-$dd $hh:$mm"
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomMonth(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("yyyyMM")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomMmDay(date: Long = System.currentTimeMillis()): String {
        val sdf = SimpleDateFormat("MM月dd日")
        return sdf.format(Date(date))

    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateCustomMmDay(date: String): String {
        val sdf = SimpleDateFormat("MM月dd日")
        var str = ""
        if (date.length > 7) {
            str = date.substring(4, 6) + "月" + date.substring(6, 8) + "日"
        }
        return str

    }

    fun getWhichMonth(date: Long? = Date().time, offset: Int = 0): String {
        val sdf = SimpleDateFormat("yyyy年MM月")
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, offset)
        return sdf.format(Date(cal.timeInMillis))

    }


    fun isSameDay(date: Long = Date().time, date0: Long = System.currentTimeMillis()): Boolean {
        var cal1: Calendar = Calendar.getInstance()
        var cal2: Calendar = Calendar.getInstance()
        var date1 = Date(date0)
        var date2 = Date(date)
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)

    }


    fun isSameDay(date: String): Boolean {
        return if (date.length >= 10) {
            var cal1: Calendar = Calendar.getInstance()
            var date1 = Date(System.currentTimeMillis())
            cal1.time = date1
            cal1.get(Calendar.YEAR).toString() == date.substring(0, 4) &&
                    (cal1.get(Calendar.MONTH) + 1) == date.substring(5, 7).toIntSafe() &&
                    cal1.get(Calendar.DAY_OF_MONTH) == date.substring(8, 10).toIntSafe()
        } else {
            false
        }

    }

    fun isSameYear(date: String): Boolean {
        return if (date.length >= 10) {
            var cal1: Calendar = Calendar.getInstance()
            var date1 = Date(System.currentTimeMillis())
            cal1.time = date1
            cal1.get(Calendar.YEAR).toString() == date.substring(0, 4)
        } else {
            false
        }

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

    fun getWeek(cal: Calendar = Calendar.getInstance()): String? {


        return when (cal.get(Calendar.DAY_OF_WEEK)) {
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

    fun getWeek(day: Date = Date()): String? {
        var cal = Calendar.getInstance()
        cal.time = day

        return when (cal.get(Calendar.DAY_OF_WEEK)) {
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

    fun getTimeInMillis(day: String?): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return if (day.isNullOrEmpty()) {
            System.currentTimeMillis()
        } else {
            sdf.parse(day).time
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

    fun getDateString(
        yy: String? = "",
        m1: String? = "",
        dd: String? = "",
        hh: String? = "",
        m2: String? = ""
    ): String {
        return "${yy}-${m1}-${dd} $hh:$m2"
    }

}