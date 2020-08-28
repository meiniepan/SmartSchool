package com.xiaoneng.ss.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.addapp.pickers.picker.DateTimePicker
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.regex.RegexUtils
import java.io.File
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

inline fun <reified T> mStartActivity(context: Context?, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context?.startActivity(intent)
}
inline fun starPhoneNum(phone:String):String {
    return if (RegexUtils.isMobileSimple(phone)) {
        phone.substring(0,3)+"****"+phone.substring(7,11)
    }else{
        phone
    }
}
inline fun getDatePick(context: Activity):DateTimePicker{
    return DateTimePicker(context, DateTimePicker.HOUR_24).apply {
//            setActionButtonTop(false)
        setDateRangeStart(2020, 1, 1)
        setDateRangeEnd(2025, 11, 11)
        setSelectedItem(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH) + 1,
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
        )

    }
}

inline fun mainLogin(context: Context){
    UserInfo.logoutSuccess()
    when (UserInfo.getUserBean().usertype) {
        "1" -> {
            mStartActivity<LoginStuActivity>(context)
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


}

fun mReadTxtFile(context: Context): String? {



    val filePath = context.getExternalFilesDir(null)?.absolutePath + File.separator + "a.txt"

    File(filePath).appendText("Burning ")
            return filePath

}

fun mDownloadFile(context: Context): String? {

    return context.getExternalFilesDir(null)?.absolutePath + File.separator + "ss.txt"

}