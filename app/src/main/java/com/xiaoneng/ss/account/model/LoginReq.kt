package com.xiaoneng.ss.module.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:54
 */
data class LoginReq(
    var phone : String,
    var vcode : String,
    var spassword : String
)