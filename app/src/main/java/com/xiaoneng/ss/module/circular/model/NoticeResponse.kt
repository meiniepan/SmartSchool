package com.xiaoneng.ss.module.circular.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class NoticeResponse(
    var data : ArrayList<NoticeBean>,
    var totle : String,
    var read : String,
    var retotle : String,
    var feedback : String,
    var lastid : String
)