package com.xiaoneng.ss.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.PowerManager
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import cn.addapp.pickers.picker.DateTimePicker
import cn.addapp.pickers.picker.SinglePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.common.constclass.Solang
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import com.xiaoneng.ss.module.school.model.SiteItemBean
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


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
    crossinline block: String.() -> Unit
) {
    DateTimePicker(this, DateTimePicker.HOUR_24).apply {
//            setActionButtonTop(false)
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(Constant.THIS_YEAR, 1, 1)
        setDateRangeEnd(Constant.THIS_YEAR + 5, 11, 11)
        setSelectedItem(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
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
        setSelectedTextColor(resources.getColor(R.color.themeColor))
        setDateRangeStart(Constant.THIS_YEAR, 1, 1)
        setDateRangeEnd(Constant.THIS_YEAR + 5, 11, 11)
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

    return context.getExternalFilesDir(null)?.absolutePath + File.separator + name

}

fun Context.mBitmap2Local(bitmap: Bitmap?, name: String): String? {
    var path = getExternalFilesDir(null)?.absolutePath + File.separator + name
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
    } else if (type == Solang.TEACHER || type == Solang.ADMIN) {
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

    val teraBytes = megaByte / 1024
    if (teraBytes < 1) {
        return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "G"
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