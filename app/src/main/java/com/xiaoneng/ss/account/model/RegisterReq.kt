package com.xiaoneng.ss.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:57
 */
data class RegisterReq(
    var phone : String,
    var vcode : String,
    var invitecode : String,
    var realname : String,//选填
    var spassword : String//选填
)