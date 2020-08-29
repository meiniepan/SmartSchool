package com.xiaoneng.ss.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:06
 */
data class StudentResp(
    var data: MutableList<StudentBean>,
    var total: String
)