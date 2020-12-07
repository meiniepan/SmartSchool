package com.xiaoneng.ss.account.model

import android.os.Build
import com.xiaoneng.ss.BuildConfig
import com.xiaoneng.ss.common.state.UserInfo
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/07
 * Time: 19:54
 */
data class UpTokenBean(
    var client_type: String = "android",
    var version: String = BuildConfig.VERSION_NAME,
    var uuid: String = UUID.randomUUID().toString(),
    var system: String = "Android"+Build.VERSION.RELEASE,
    var devicetoken: String = UserInfo.getUserBean().devicetoken?:"",
    var uid: String = UserInfo.getUserBean().uid?:"",
    var usertype: String = UserInfo.getUserBean().usertype?:"",
    var remark: String? = null

)