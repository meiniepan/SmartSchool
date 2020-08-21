package com.xiaoneng.ss.common.utils

import android.content.Context
import android.content.Intent
import com.xiaoneng.ss.common.utils.regex.RegexUtils

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