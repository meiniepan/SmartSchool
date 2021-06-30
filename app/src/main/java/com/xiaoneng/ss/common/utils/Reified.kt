package com.xiaoneng.ss.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.PowerManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import cn.addapp.pickers.common.LineConfig
import cn.addapp.pickers.picker.SinglePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.common.constclass.Solang
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.custom.DateRangePicker
import com.xiaoneng.ss.custom.DateTimePicker
import com.xiaoneng.ss.custom.widgets.*
import com.xiaoneng.ss.module.school.model.QuantizeBody
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.SiteItemBean
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @CreateDate: 2020/5/1 16:34
 */

inline fun <reified T> mStartActivity(context: Context?) {
    val intent = Intent(context, T::class.java)
    context?.startActivity(intent)
}

inline fun <reified T> mStartForResult(context: Activity?, code: Int, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.startActivityForResult(intent, code)
}


inline fun <reified T> mStartActivity(context: Context?, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.startActivity(intent)
}

inline fun formatStarPhoneNum(phone: String?): String? {
    return if (RegexUtils.isMobileSimple(phone)) {
        phone?.substring(0, 3) + "****" + phone?.substring(7, 11)
    } else {
        phone
    }
}

inline fun Activity.showDatePick(
    textView1: TextView,
    textView2: TextView,
    beginTime: Long = System.currentTimeMillis(),
    crossinline block: String.() -> Unit
) {
    val THIS_YEAR:Int = Calendar.getInstance().get(Calendar.YEAR)
    DateTimePicker(this, DateTimePicker.HOUR_24).apply {
//            setActionButtonTop(false)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(THIS_YEAR, 1, 1)
        setDateRangeEnd(THIS_YEAR + 5, 11, 11)
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = beginTime
        setSelectedItem(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )

        setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                setSelectedTextColor(resources.getColor(R.color.themeColor))
                val sdf = SimpleDateFormat("yyyyMMdd")
                var week = DateUtil.getWeek(sdf.parse("${year}${month}${day}"))
                var time1 = "${month}月${day}日 $week"
                var time2 = "${hour}:${minute}"
                DateUtil.getDateString(year, month, day, hour, minute).block()
                textView1.text = time1
                textView2.text = time2
            }

        })
        show()
    }

}

inline fun Activity.showDateDayPick(textView: TextView, crossinline block: String.() -> Unit) {
    DateTimePicker(this, DateTimePicker.NONE).apply {
//            setActionButtonTop(false)
        val THIS_YEAR:Int = Calendar.getInstance().get(Calendar.YEAR)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(THIS_YEAR, 1, 1)
        setDateRangeEnd(THIS_YEAR + 5, 11, 11)
        setSelectedItem(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
        )

        setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                var timess = "${month}月${day}日"
                "${year}${month}${day}".block()
                textView.text = timess
            }

        })
        show()
    }

}

inline fun Activity.showDateDayHourPick(textView: TextView, crossinline block: String.() -> Unit) {
    DateTimePicker(this, DateTimePicker.HOUR_24).apply {
//            setActionButtonTop(false)
        val THIS_YEAR:Int = Calendar.getInstance().get(Calendar.YEAR)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(THIS_YEAR, 1, 1)
        setDateRangeEnd(THIS_YEAR + 5, 11, 11)
        setSelectedItem(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            0
        )

        setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                var timess = "${month}月${day}日 ${hour}:00"
                "${year}${month}${day}${hour}".block()
                textView.text = timess
            }

        })
        show()
    }

}

inline fun Activity.showBirthDayPick(textView: TextView, crossinline block: String.() -> Unit) {
    DateTimePicker(this, DateTimePicker.NONE).apply {
//            setActionButtonTop(false)
        val THIS_YEAR:Int = Calendar.getInstance().get(Calendar.YEAR)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(1900, 1, 1)
        setDateRangeEnd(THIS_YEAR, 12, 31)
        setSelectedItem(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
        )

        setOnDateTimePickListener(object : DateTimePicker.OnYearMonthDayTimePickListener {
            override fun onDateTimePicked(
                year: String?,
                month: String?,
                day: String?,
                hour: String?,
                minute: String?
            ) {
                var timess = "${month}月${day}日"
                "${year}${month}${day}".block()
                textView.text = timess
            }

        })
        show()
    }

}

inline fun Activity.showTimeSection(textView: TextView, crossinline block: String.() -> Unit, crossinline blockEnd: String.() -> Unit) {
    DateRangePicker(this, DateRangePicker.NONE).apply {
//            setActionButtonTop(false)
        val THIS_YEAR:Int = Calendar.getInstance().get(Calendar.YEAR)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(THIS_YEAR, 1, 1)
        setDateRangeEnd(THIS_YEAR + 5, 11, 11)

        val config = LineConfig()
        config.setColor(-0x120000) //线颜色
        config.setAlpha(140) //线透明度

        setLineConfig(config)
        var calendar = Calendar.getInstance()
        setSelectedItem(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        setOnDateTimePickListener(object : DateRangePicker.OnYearMonthDayRangePickListener {
            override fun onDateTimePicked(
                year: String?,
                monthStart: String?,
                dayStart: String?,
                monthEnd: String?,
                dayEnd: String?
            ) {
                var yearEnd = year
                if (monthEnd.toIntSafe()<monthStart.toIntSafe()){
                    yearEnd = (year.toIntSafe()+1).toString()
                }
                calendar.set(Calendar.YEAR, year.toIntSafe());
                calendar.set(Calendar.MONTH, monthStart.toIntSafe()-1);
                calendar.set(Calendar.DATE, dayStart.toIntSafe());
                var aTime=calendar.getTimeInMillis();
                calendar.set(Calendar.YEAR, yearEnd.toIntSafe());
                calendar.set(Calendar.MONTH, monthEnd.toIntSafe()-1);
                calendar.set(Calendar.DATE, dayEnd.toIntSafe());
                var bTime=calendar.getTimeInMillis();
                var cTime=bTime-aTime
                var days = cTime/(1000*60*60*24)
                var timess = "${year}年${monthStart}月${dayStart}日-${yearEnd}年${monthEnd}月${dayEnd}日(${days}天)"
                "${year}${monthStart}${dayStart}".block()
                "${year}${monthEnd}${dayEnd}".blockEnd()
                textView.text = timess
            }


        })
        show()
    }

}

inline fun Activity.showSexPick(textView: TextView, crossinline block: String.() -> Unit) {
    SinglePicker(this, arrayOf("男", "女", "未知")).apply {
//            setActionButtonTop(false)
        setCanLoop(false)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setOnItemPickListener { index, item ->
            item.block()
            textView.text = item
        }

        show()
    }

}

inline fun mainLogin(context: Context) {
    when (UserInfo.getUserBean().usertype) {
        "1" -> {
            if (UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_STU) {

                mStartActivity<LoginStuActivity>(context)
            } else {
                mStartActivity<LoginTeacherActivity>(context) {
                    putExtra(Constant.FLAG, false)
                }
            }
        }
        "2" -> {
            mStartActivity<LoginTeacherActivity>(context)
        }

        "99" -> {
            mStartActivity<LoginTeacherActivity>(context)
        }
        else -> {
            mStartActivity<LoginSwitchActivity>(context)
        }
    }

    UserInfo.logoutSuccess()

}


inline fun mDownloadFile(context: Context, name: String?): String? {
    return PathSelector(BaseApplication.instance).getXiaonengPath() + File.separator + name
}

fun Context.mBitmap2Local(bitmap: Bitmap?, name: String): String? {
    var path = PathSelector(BaseApplication.instance).getXiaonengPath() + File.separator + name
    val filename = File(path)

    if (bitmap != null) {
        try {
            if (!filename.exists()) {
                filename.parentFile.mkdirs()
                filename.createNewFile()
            }
            val out = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            return path
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return ""
}

/**
 * 设置drawable的corner
 */
inline fun getCornerRadii(
    leftTop: Float, rightTop: Float,
    leftBottom: Float, rightBottom: Float
): FloatArray {
    //这里返回的一个浮点型的数组，一定要有8个元素，不然会报错
    return floatArrayOf(
        dp2px(leftTop),
        dp2px(leftTop),
        dp2px(rightTop),
        dp2px(rightTop),
        dp2px(rightBottom),
        dp2px(rightBottom),
        dp2px(leftBottom),
        dp2px(leftBottom)
    )
}

/**
 * 格式化网络请求返回的response
 */
inline fun <reified T> netResponseFormat(response: Any): T? {
    val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
    val jsonString = gson.toJson(response)
    val resultType = object : TypeToken<T>() {}.type
    var result = gson.fromJson<T>(jsonString, resultType)
    return result
}

fun dealTemplate(
    activity: Activity,
    root: ViewGroup,
    template: ArrayList<QuantizeTemplateBean>,
    mDataCommit: QuantizeBody
){
    if (template.size>0){
        for (i in 0 until template.size){
            if (template[i].name=="Input"){
                root.addView(ViewTextSingle(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="Textarea"){
                root.addView(ViewTextAera(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="InputNumber"){
                root.addView(ViewNumber(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="Radio"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="Checkbox"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="DatePicker"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="DatePickerMultiple"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="DateTimePicker"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="CascaderClass"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }else if (template[i].name=="ChoseStudents"){
                root.addView(ViewJump(activity,data = template[i],commit = mDataCommit))
            }
        }
    }
}


fun Context.mAlert(
    message: String,
    title: String? = null,
    confirmText: String? = null,
    cancelText: String? = null,
    cancelOutside: Boolean = true,
    onConfirm: () -> Unit
) {
    MaterialDialog(this).show {
        title(text = title ?: "温馨提示")
        message(text = message)
        cornerRadius(8.0f)
        positiveButton(text = getString(R.string.doneM))
        positiveButton {
            onConfirm()
        }
        cancelOnTouchOutside(cancelOutside)
    }
}

inline fun getOssObjectKey(@Solang.UserType type: String?, id: String?, fileName: String?): String {
    var mType = ""
    if (type == Solang.STUDENT) {
        mType = "student/"
    } else {
        mType = "teacher/"
    }
    var result = "$mType$id/avatar/$fileName"
    return result
}

fun getBooleanString(bool: Boolean): String {
    return if (bool) {
        "1"
    } else {
        "0"
    }

}

fun getStringBoolean(value: String?): Boolean {
    return value == "1"

}

@SuppressLint("NewApi")
fun Context.isSystemWhiteList(): Boolean {
    val pm: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
    val packageName: String = packageName
    val isWhite =
        pm.isIgnoringBatteryOptimizations(packageName)
    return isWhite
}

fun Context.captchaToast(code: String?) {
    code?.let {
        mToast("验证码已发送")
    }
}

fun initSiteTimes(): ArrayList<SiteItemBean> {
    var mSiteData: ArrayList<SiteItemBean> = ArrayList()
    mSiteData.add(SiteItemBean(timeStr = "上午00:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午00:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午01:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午01:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午02:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午02:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午03:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午03:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午04:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午04:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午05:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午05:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午06:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午06:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午07:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午07:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午08:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午08:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午09:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午09:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午10:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午10:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午11:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午11:30"))
    mSiteData.add(SiteItemBean(timeStr = "上午12:00"))
    mSiteData.add(SiteItemBean(timeStr = "上午12:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午01:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午01:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午02:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午02:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午03:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午03:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午04:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午04:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午05:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午05:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午06:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午06:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午07:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午07:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午08:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午08:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午09:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午09:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午10:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午10:30"))
    mSiteData.add(SiteItemBean(timeStr = "下午11:00"))
    mSiteData.add(SiteItemBean(timeStr = "下午11:30"))
    return mSiteData

}

fun initSiteTimes24(): ArrayList<SiteItemBean> {
    var mSiteData: ArrayList<SiteItemBean> = ArrayList()
    mSiteData.add(SiteItemBean(timeStr = "00:00"))
    mSiteData.add(SiteItemBean(timeStr = "00:30"))
    mSiteData.add(SiteItemBean(timeStr = "01:00"))
    mSiteData.add(SiteItemBean(timeStr = "01:30"))
    mSiteData.add(SiteItemBean(timeStr = "02:00"))
    mSiteData.add(SiteItemBean(timeStr = "02:30"))
    mSiteData.add(SiteItemBean(timeStr = "03:00"))
    mSiteData.add(SiteItemBean(timeStr = "03:30"))
    mSiteData.add(SiteItemBean(timeStr = "04:00"))
    mSiteData.add(SiteItemBean(timeStr = "04:30"))
    mSiteData.add(SiteItemBean(timeStr = "05:00"))
    mSiteData.add(SiteItemBean(timeStr = "05:30"))
    mSiteData.add(SiteItemBean(timeStr = "06:00"))
    mSiteData.add(SiteItemBean(timeStr = "06:30"))
    mSiteData.add(SiteItemBean(timeStr = "07:00"))
    mSiteData.add(SiteItemBean(timeStr = "07:30"))
    mSiteData.add(SiteItemBean(timeStr = "08:00"))
    mSiteData.add(SiteItemBean(timeStr = "08:30"))
    mSiteData.add(SiteItemBean(timeStr = "09:00"))
    mSiteData.add(SiteItemBean(timeStr = "09:30"))
    mSiteData.add(SiteItemBean(timeStr = "10:00"))
    mSiteData.add(SiteItemBean(timeStr = "10:30"))
    mSiteData.add(SiteItemBean(timeStr = "11:00"))
    mSiteData.add(SiteItemBean(timeStr = "11:30"))
    mSiteData.add(SiteItemBean(timeStr = "12:00"))
    mSiteData.add(SiteItemBean(timeStr = "12:30"))
    mSiteData.add(SiteItemBean(timeStr = "13:00"))
    mSiteData.add(SiteItemBean(timeStr = "13:30"))
    mSiteData.add(SiteItemBean(timeStr = "14:00"))
    mSiteData.add(SiteItemBean(timeStr = "14:30"))
    mSiteData.add(SiteItemBean(timeStr = "15:00"))
    mSiteData.add(SiteItemBean(timeStr = "15:30"))
    mSiteData.add(SiteItemBean(timeStr = "16:00"))
    mSiteData.add(SiteItemBean(timeStr = "16:30"))
    mSiteData.add(SiteItemBean(timeStr = "17:00"))
    mSiteData.add(SiteItemBean(timeStr = "17:30"))
    mSiteData.add(SiteItemBean(timeStr = "18:00"))
    mSiteData.add(SiteItemBean(timeStr = "18:30"))
    mSiteData.add(SiteItemBean(timeStr = "19:00"))
    mSiteData.add(SiteItemBean(timeStr = "19:30"))
    mSiteData.add(SiteItemBean(timeStr = "20:00"))
    mSiteData.add(SiteItemBean(timeStr = "20:30"))
    mSiteData.add(SiteItemBean(timeStr = "21:00"))
    mSiteData.add(SiteItemBean(timeStr = "21:30"))
    mSiteData.add(SiteItemBean(timeStr = "22:00"))
    mSiteData.add(SiteItemBean(timeStr = "22:30"))
    mSiteData.add(SiteItemBean(timeStr = "23:00"))
    mSiteData.add(SiteItemBean(timeStr = "23:30"))
    mSiteData.add(SiteItemBean(timeStr = "00:00"))
    return mSiteData

}

fun getSiteTimeByPosition(begin: Int, end: Int = begin): String {
    var mB = begin
    var mE = end + 1
    if (begin < 0 || begin > 47) {
        mB = 0
    }
    if (end < 0 || end > 47) {
        mE = 0
    }
    return initSiteTimes()[mB].timeStr + "-" + initSiteTimes()[mE].timeStr
}

fun String?.toIntSafe(): Int {
    var result = 0
    return try {
        this!!.toInt()
    } catch (e: java.lang.Exception) {
        result
    }

}

fun String?.toLongSafe(): Long {
    var result = 0L
    return try {
        this!!.toLong()
    } catch (e: java.lang.Exception) {
        result
    }

}

fun String?.isImage(): Boolean {
    this?.let {
        if (it.contains("image/")) {
            return true
        }
    }
    return false
}

fun String?.endIsImage(): Boolean {
    this?.let {
      var it = it.toLowerCase()
        if (it.endsWith(".jpg")
            ||it.endsWith(".png")
            ||it.endsWith(".jpeg")
            ||it.endsWith(".gif")
            ||it.endsWith(".bmp")
        ) {
            return true
        }
    }
    return false
}
fun String?.endIsVideo(): Boolean {
    this?.let {
        var it = it.toLowerCase()
        if (it.endsWith(".avi")
            ||it.endsWith(".rmvb")
            ||it.endsWith(".rm")
            ||it.endsWith(".mov")
            ||it.endsWith(".flv")
            ||it.endsWith(".mp4")
            ||it.endsWith(".3gp")
            ||it.endsWith(".m4v")
            ||it.endsWith(".m3u8")
            ||it.endsWith(".webm")
        ) {
            return true
        }
    }
    return false
}

fun String?.getFileIcon(): Int {
    var srcId = -1
    this?.let {
        if (it.endIsImage()) {
            srcId =  R.drawable.ic_type_image
        } else if (it.endIsVideo()) {
            srcId =  R.drawable.ic_type_video
        } else if (it.endsWith(".doc") || it.endsWith(".docx")) {
            srcId =  R.drawable.ic_type_word
        } else if (it.endsWith(".xls") || it.endsWith(".xlsx")) {
            srcId =  R.drawable.ic_type_excel
        } else if (it.endsWith(".pdf")) {
            srcId =  R.drawable.ic_type_pdf
        } else if (it.endsWith(".zip") || it.endsWith(".rar") || it.endsWith(
                ".tar"
            ) || it.endsWith(
                ".gz"
            )
        ) {
            srcId =  R.drawable.ic_type_zip
        } else {
            srcId =  R.drawable.ic_type_unknow
        }
    }
    return srcId
}

fun Long.formatMemorySize(): String {
    val kiloByte = this / 1024.toDouble()

    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
        return kiloByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "K"
    }

    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
        return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "M"
    }

    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
        return gigaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "G"
    }

    return teraBytes.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "T"
}

inline fun Context.mToast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }

inline fun Context.mToast(message: Int): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }

inline fun <reified T> mFromJson(response: String?): T? {
    val gson =Gson()
    val resultType = object : TypeToken<T>() {}.type
    var result:T? = null
    try {
     result = gson.fromJson<T>(response, resultType)
    } catch (e: Exception) {

    }
    return result
}