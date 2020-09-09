package com.xiaoneng.ss.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.TextView
import cn.addapp.pickers.picker.DateTimePicker
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import java.io.File
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

inline fun <reified T> mStartForResult(context: Activity?,code:Int) {
    val intent = Intent(context, T::class.java)
    context?.startActivityForResult(intent,code)
}

inline fun <reified T> mSetResult(context: Activity?,code:Int, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.setResult(code,intent)
}

inline fun <reified T> mStartActivity(context: Context?, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.startActivity(intent)
}

inline fun starPhoneNum(phone: String): String {
    return if (RegexUtils.isMobileSimple(phone)) {
        phone.substring(0, 3) + "****" + phone.substring(7, 11)
    } else {
        phone
    }
}

inline fun Activity.showDatePick(textView1: TextView,textView2: TextView, crossinline block: String.() -> Unit) {
     DateTimePicker(this, DateTimePicker.HOUR_24).apply {
//            setActionButtonTop(false)
        setDateRangeStart(Constant.THIS_YEAR, 1, 1)
        setDateRangeEnd(Constant.THIS_YEAR+5, 11, 11)
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
                val sdf = SimpleDateFormat("yyyyMMdd")
               var week = DateUtil.getWeek(sdf.parse("${year}${month}${day}"))
                var time1 = "${month}月${day}日 $week"
                var time2 = "${hour}:${minute}"
                DateUtil.getDateString(year,month,day,hour,minute).block()
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
        setDateRangeStart(Constant.THIS_YEAR, 1, 1)
        setDateRangeEnd(Constant.THIS_YEAR+5, 11, 11)
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


inline fun mDownloadFile(context: Context, name: String): String? {

    return context.getExternalFilesDir(null)?.absolutePath + File.separator + name

}

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

inline fun <reified T> netResponseFormat(response: Any): T? {
    val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
    val jsonString = gson.toJson(response)
    val resultType = object : TypeToken<T>() {}.type
    var result = gson.fromJson<T>(jsonString, resultType)
    return result
}


fun Context.mAlert(message: String, confirmText: String? = null, cancelText: String? = null, onConfirm: () -> Unit) {
    MaterialDialog(this).show {
        title(R.string.title)
        message(text = message)
        cornerRadius(8.0f)
        positiveButton(text = getString(R.string.done))
        positiveButton {
            onConfirm()
        }
    }
}