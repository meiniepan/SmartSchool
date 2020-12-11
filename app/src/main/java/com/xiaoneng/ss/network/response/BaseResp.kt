package com.xiaoneng.ss.network.response

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/11
 * Time: 17:06
 */
data class BaseResp<T>(
    var data: ArrayList<T>?
)