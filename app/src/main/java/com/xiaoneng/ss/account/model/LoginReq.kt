package com.xiaoneng.ss.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:54
 */
data class LoginReq(
    var phone: String,
    var vcode: String? = null,
    var spassword: String? = null
)