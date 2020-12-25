package com.xiaoneng.ss.common.utils

import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.model.ScheduleDayBean
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Burning
 * @description:
 * @date :2020/8/25 10:58 AM
 */
class Lunar(cal: Calendar) {
    private val year: Int
    private val month: Int
    private val day: Int
    private var leap: Boolean

    //====== 传回农历 y年的生肖
    fun animalsYear(): String {
        val Animals =
            arrayOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")
        return Animals[(year - 4) % 12]
    }

    //====== 传入 offset 传回干支, 0=甲子
    fun cyclical(): String {
        val num = year - 1900 + 36
        return cyclicalm(num)
    }

    // TODO Auto-generated method stub
    val lunarMonthString: String?
        get() =// TODO Auto-generated method stub
            null

    val chinaDayString: String
        get() {
            val chineseTen = arrayOf("初", "十", "廿", "三")
            val n = if (day % 10 == 0) 9 else day % 10 - 1
            if (day > 30) {
                return ""
            }
            return if (day == 10) {
                "初十"
            } else {
                chineseTen[day / 10] + chineseNumber[n]
            }
        }

    override fun toString(): String {
        return (if (leap) "闰" else "") + chineseNumber[month - 1] + "月" + getChinaDayString(
            day
        )
    }

    companion object {
        val chineseNumber =
            arrayOf("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二")
        var chineseDateFormat =
            SimpleDateFormat("yyyy年MM月dd日")
        val lunarInfo = longArrayOf(
            0x04bd8,
            0x04ae0,
            0x0a570,
            0x054d5,
            0x0d260,
            0x0d950,
            0x16554,
            0x056a0,
            0x09ad0,
            0x055d2,
            0x04ae0,
            0x0a5b6,
            0x0a4d0,
            0x0d250,
            0x1d255,
            0x0b540,
            0x0d6a0,
            0x0ada2,
            0x095b0,
            0x14977,
            0x04970,
            0x0a4b0,
            0x0b4b5,
            0x06a50,
            0x06d40,
            0x1ab54,
            0x02b60,
            0x09570,
            0x052f2,
            0x04970,
            0x06566,
            0x0d4a0,
            0x0ea50,
            0x06e95,
            0x05ad0,
            0x02b60,
            0x186e3,
            0x092e0,
            0x1c8d7,
            0x0c950,
            0x0d4a0,
            0x1d8a6,
            0x0b550,
            0x056a0,
            0x1a5b4,
            0x025d0,
            0x092d0,
            0x0d2b2,
            0x0a950,
            0x0b557,
            0x06ca0,
            0x0b550,
            0x15355,
            0x04da0,
            0x0a5d0,
            0x14573,
            0x052d0,
            0x0a9a8,
            0x0e950,
            0x06aa0,
            0x0aea6,
            0x0ab50,
            0x04b60,
            0x0aae4,
            0x0a570,
            0x05260,
            0x0f263,
            0x0d950,
            0x05b57,
            0x056a0,
            0x096d0,
            0x04dd5,
            0x04ad0,
            0x0a4d0,
            0x0d4d4,
            0x0d250,
            0x0d558,
            0x0b540,
            0x0b5a0,
            0x195a6,
            0x095b0,
            0x049b0,
            0x0a974,
            0x0a4b0,
            0x0b27a,
            0x06a50,
            0x06d40,
            0x0af46,
            0x0ab60,
            0x09570,
            0x04af5,
            0x04970,
            0x064b0,
            0x074a3,
            0x0ea50,
            0x06b58,
            0x055c0,
            0x0ab60,
            0x096d5,
            0x092e0,
            0x0c960,
            0x0d954,
            0x0d4a0,
            0x0da50,
            0x07552,
            0x056a0,
            0x0abb7,
            0x025d0,
            0x092d0,
            0x0cab5,
            0x0a950,
            0x0b4a0,
            0x0baa4,
            0x0ad50,
            0x055d9,
            0x04ba0,
            0x0a5b0,
            0x15176,
            0x052b0,
            0x0a930,
            0x07954,
            0x06aa0,
            0x0ad50,
            0x05b52,
            0x04b60,
            0x0a6e6,
            0x0a4e0,
            0x0d260,
            0x0ea65,
            0x0d530,
            0x05aa0,
            0x076a3,
            0x096d0,
            0x04bd7,
            0x04ad0,
            0x0a4d0,
            0x1d0b6,
            0x0d250,
            0x0d520,
            0x0dd45,
            0x0b5a0,
            0x056d0,
            0x055b2,
            0x049b0,
            0x0a577,
            0x0a4b0,
            0x0aa50,
            0x1b255,
            0x06d20,
            0x0ada0
        )

        //====== 传回农历 y年的总天数
        private fun yearDays(y: Int): Int {
            var i: Int
            var sum = 348
            i = 0x8000
            while (i > 0x8) {
                if (lunarInfo[y - 1900] and i.toLong() != 0L) {
                    sum += 1
                }
                i = i shr 1
            }
            return sum + leapDays(y)
        }

        //====== 传回农历 y年闰月的天数
        private fun leapDays(y: Int): Int {
            return if (leapMonth(y) != 0) {
                if (lunarInfo[y - 1900] and 0x10000 != 0L) {
                    30
                } else {
                    29
                }
            } else {
                0
            }
        }

        //====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
        private fun leapMonth(y: Int): Int {
            return (lunarInfo[y - 1900] and 0xf).toInt()
        }

        //====== 传回农历 y年m月的总天数
        private fun monthDays(y: Int, m: Int): Int {
            return if (lunarInfo[y - 1900] and (0x10000 shr m).toLong() == 0L) {
                29
            } else {
                30
            }
        }

        //====== 传入 月日的offset 传回干支, 0=甲子
        private fun cyclicalm(num: Int): String {
            val Gan =
                arrayOf("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸")
            val Zhi =
                arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
            return Gan[num % 10] + Zhi[num % 12]
        }

        fun getChinaDayString(day: Int): String {
            val chineseTen = arrayOf("初", "十", "廿", "三")
            val n = if (day % 10 == 0) 9 else day % 10 - 1
            if (day > 30) {
                return ""
            }
            return if (day == 10) {
                "初十"
            } else {
                chineseTen[day / 10] + chineseNumber[n]
            }
        }

        val week: String
            get() {
                val cal = Calendar.getInstance()
                val i = cal[Calendar.DAY_OF_WEEK]
                return when (i) {
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

        fun getWeek(day: Int): String {
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

        fun getWhichWeek(chosenDay: Long?): String {
            val cal = Calendar.getInstance()
            if (chosenDay != 0L) {
                cal.timeInMillis = chosenDay!!
            }
            val i = cal[Calendar.WEEK_OF_YEAR]
            return "第" + i + "周"
        }

        @Throws(ParseException::class)
        fun getFirstAndLastOfWeek(
            dataStr: String?,
            dateFormat: String?,
            resultDateFormat: String?
        ): String {
            val cal = Calendar.getInstance()
            cal.time = SimpleDateFormat(dateFormat).parse(dataStr)
            var d = 0
            d = if (cal[Calendar.DAY_OF_WEEK] == 1) {
                -6
            } else {
                2 - cal[Calendar.DAY_OF_WEEK]
            }
            cal.add(Calendar.DAY_OF_WEEK, d)
            // 所在周开始日期
            val data1 =
                SimpleDateFormat(resultDateFormat).format(cal.time)
            cal.add(Calendar.DAY_OF_WEEK, 6)
            // 所在周结束日期
            val data2 =
                SimpleDateFormat(resultDateFormat).format(cal.time)
            return data1 + "_" + data2
        }

        fun getCurrentDaysOfWeek(chosenDay: Long?): ArrayList<DayBean> {
            val cal = Calendar.getInstance()
            val calToday = Calendar.getInstance()
            if (chosenDay != 0L) {
                cal.timeInMillis = chosenDay!!
                calToday.timeInMillis = chosenDay
            }
            var d = 0
//            d = if (cal[Calendar.DAY_OF_WEEK] == 1) {
//                -7
//            } else {
               d= 1 - cal[Calendar.DAY_OF_WEEK]
//            }
            cal.add(Calendar.DAY_OF_WEEK, d)
            // 所在周开始日期
            val list = ArrayList<DayBean>()
            var isToday = false
            for (i in 0..6) {
                isToday = calToday[Calendar.DAY_OF_WEEK] == cal[Calendar.DAY_OF_WEEK]
                val bean = DayBean(
                    cal.timeInMillis, cal[Calendar.DAY_OF_MONTH].toString() + "",
                    Lunar(cal).chinaDayString,
                    isToday,
                    true,
                    ArrayList()
                )
                list.add(bean)
                cal.add(Calendar.DAY_OF_WEEK, 1)
            }
            return list
        }

        fun getCurrentDaysOfMonth(
            data: ArrayList<ScheduleDayBean>,
            chosenDay: Long? = null
        ): ArrayList<DayBean> {
            val cal = Calendar.getInstance()
            val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            val calToday = Calendar.getInstance()
            if (chosenDay!=null){
                calToday.timeInMillis = chosenDay
            }
            var d = cal[Calendar.DAY_OF_MONTH]
            d = 1 - d
            // 所在月开始日期
            cal.add(Calendar.DAY_OF_WEEK, d)
            //计算开始日期是星期几
            val which = cal[Calendar.DAY_OF_WEEK]
            val beginNum = which - 1
            val list = ArrayList<DayBean>()
            var bean = DayBean()
            if (beginNum > 0) {
                for (i in 0 until beginNum) {
                    list.add(bean)
                }
            }
            var isToday = false
            for (i in 0 until maxDays) {
                isToday = calToday[Calendar.DAY_OF_MONTH] == cal[Calendar.DAY_OF_MONTH]
                var beanList = ArrayList<ScheduleBean>()
                var beanRemove = ArrayList<ScheduleDayBean>()
                for (beans in data) {
                    if (DateUtil.formatDateCustomDay(cal.timeInMillis) == beans.day) {
                        beanList.addAll(beans.list)
                        beanRemove.add(beans)
                    }
                }
                if (beanRemove.size > 0) {
                    data.removeAll(beanRemove)
                }
                bean = DayBean(
                    cal.timeInMillis, cal[Calendar.DAY_OF_MONTH].toString() + "",
                    Lunar(cal).chinaDayString,
                    isToday,
                    true,
                    beanList
                )
                list.add(bean)
                cal.add(Calendar.DAY_OF_WEEK, 1)
            }
            return list
        }

        fun getDaysOfMonth(chosenDay: Long= System.currentTimeMillis(),offset: Int= 0): ArrayList<DayBean> {
            val cal = Calendar.getInstance()
            cal.add(Calendar.MONTH, offset)
            val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            val calToday = Calendar.getInstance()
            calToday.timeInMillis = chosenDay
            var d = cal[Calendar.DAY_OF_MONTH]
            d = 1 - d
            // 所在月开始日期
            cal.add(Calendar.DAY_OF_WEEK, d)
            //计算开始日期是星期几
            val which = cal[Calendar.DAY_OF_WEEK]
            val beginNum = which - 1
            val list = ArrayList<DayBean>()
            var bean = DayBean()
            if (beginNum > 0) {
                for (i in 0 until beginNum) {
                    list.add(bean)
                }
            }
            var isToday = false
            for (i in 0 until maxDays) {
                isToday = DateUtil.isSameDay(cal.timeInMillis,calToday.timeInMillis)
                var beanList = ArrayList<ScheduleBean>()

                bean = DayBean(
                    cal.timeInMillis, cal[Calendar.DAY_OF_MONTH].toString() + "",
                    Lunar(cal).chinaDayString,
                    isToday,
                    true,
                    beanList
                )
                list.add(bean)
                cal.add(Calendar.DAY_OF_WEEK, 1)
            }
            return list
        }
    }



    init {
        val yearCyl: Int
        var monCyl: Int
        val dayCyl: Int
        var leapMonth = 0
        var baseDate: Date? = null
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日")
        } catch (e: ParseException) {
            e.printStackTrace() //To change body of catch statement use Options | File Templates.
        }

        //求出和1900年1月31日相差的天数
        var offset = ((cal.time.time - baseDate!!.time) / 86400000L).toInt()
        dayCyl = offset + 40
        monCyl = 14

        //用offset减去每农历年的天数
        // 计算当天是农历第几天
        //i最终结果是农历的年份
        //offset是当年的第几天
        var iYear: Int
        var daysOfYear = 0
        iYear = 1900
        while (iYear < 2050 && offset > 0) {
            daysOfYear = yearDays(iYear)
            offset -= daysOfYear
            monCyl += 12
            iYear++
        }
        if (offset < 0) {
            offset += daysOfYear
            iYear--
            monCyl -= 12
        }
        //农历年份
        year = iYear
        yearCyl = iYear - 1864
        leapMonth = leapMonth(iYear) //闰哪个月,1-12
        leap = false

        //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        var iMonth: Int
        var daysOfMonth = 0
        iMonth = 1
        while (iMonth < 13 && offset > 0) {

            //闰月
            if (leapMonth > 0 && iMonth == leapMonth + 1 && !leap) {
                --iMonth
                leap = true
                daysOfMonth = leapDays(year)
            } else {
                daysOfMonth = monthDays(year, iMonth)
            }
            offset -= daysOfMonth
            //解除闰月
            if (leap && iMonth == leapMonth + 1) {
                leap = false
            }
            if (!leap) {
                monCyl++
            }
            iMonth++
        }
        //offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false
            } else {
                leap = true
                --iMonth
                --monCyl
            }
        }
        //offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth
            --iMonth
            --monCyl
        }
        month = iMonth
        day = offset + 1
    }
}