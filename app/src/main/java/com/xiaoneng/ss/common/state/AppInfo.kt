package com.xiaoneng.ss.common.state

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.account.model.AppBean
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/10
 * Time: 19:30
 */
object AppInfo {

    var emptyJson = Gson().toJson(ArrayList<AppBean>())
    var appInfoJson: String by SPreference(Constant.APP_INFO, emptyJson)


    fun modifyAppInfo(response: ArrayList<AppBean>) {
        appInfoJson = Gson().toJson(response)
    }


    fun getAppInfo(): ArrayList<AppBean> {
        val resultType = object : TypeToken<ArrayList<AppBean>>() {}.type
        val gson = Gson()
        return gson.fromJson<ArrayList<AppBean>>(appInfoJson, resultType)
    }

    fun checkRule1(key: String): Boolean {
        val beans = getAppInfo()
        beans.forEach {
            if (it.url == key) {
                return it.choice == "1"
            }
        }
        return false
    }

    fun checkRule2(key: String): Boolean {
        val beans = getAppInfo()
        beans.forEach {
            it.items?.forEach { item ->
                if (item.url == key) {
                    return item.choice == "1"
                }
            }
        }
        return false
    }
}