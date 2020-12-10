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

    fun checkRule(key0:String,key1:String? = null):Boolean {
        var beans = getAppInfo()
        beans.forEach {
            if (it.url==key0){
                if (key1==null){
                    if (it.choice =="1"){
                        return true
                    }
                }else{
                    it.items?.forEach {item->
                        if (item.url==key1){
                            if (item.choice=="1"){
                                return true
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}