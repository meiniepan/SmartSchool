package com.xiaoneng.ss.common.state

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.common.state.callback.CollectListener
import com.xiaoneng.ss.common.utils.AppManager
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:30
 */
object UserInfo {

    private var isLogin: Boolean by SPreference(Constant.LOGIN_KEY, false)
    var emptyJson = Gson().toJson(UserBean(""))
    var userInfoJson: String by SPreference(Constant.USER_INFO, emptyJson)

//    var token: String  = "683fa08d7b0e133c3a96859b04cc1fea"

    // 设置默认状态
    var mState: UserState = if (isLogin) LoginState() else LogoutState()


    // 收藏
    fun collect(context: Context, position: Int, listener: CollectListener) {
        mState.collect(context, position, listener)
    }


    // 跳转去登录
    fun login(context: Activity) {
        mState.login(context)
    }

    fun loginSuccess(response: UserBean) {
        // 改变 sharedPreferences   isLogin值
        isLogin = true
        mState = LoginState()
        userInfoJson = Gson().toJson(response)

    }

    fun modifyUserBean(response: UserBean) {
        // 改变 sharedPreferences   isLogin值
        response.token = getUserBean().token
        userInfoJson = Gson().toJson(response)

    }


    fun getUserBean(): UserBean {
        val resultType = object : TypeToken<UserBean>() {}.type
        val gson = Gson()
        return gson.fromJson<UserBean>(userInfoJson, resultType)
    }

    fun logoutSuccess() {
        isLogin = false
        var bean = getUserBean()
        bean.token = ""
        userInfoJson = Gson().toJson(bean)
        AppManager.finishAllActivity()
    }
}