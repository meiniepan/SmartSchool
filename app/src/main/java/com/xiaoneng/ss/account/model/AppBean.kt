package com.xiaoneng.ss.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/07
 * Time: 19:54
 */
data class AppBean(
    //1、管理员
    //2、维修
    //3、班主任
    //4、学生
    //5、家长
    //6、课任老师
    var icon: String? = null,
    var id: String? = null,
    var model: String? = null,
    var name: String? = null,
    var parent: String? = null,
    var choice: String? = null,
    var url: String? = null,
    var rulename: String? = null,
    var lang: String? = null,
    var items: ArrayList<AppItemBean>? = null
)