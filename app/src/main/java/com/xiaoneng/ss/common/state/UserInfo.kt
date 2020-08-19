package com.xiaoneng.ss.common.state

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.account.model.LoginResponse
import com.xiaoneng.ss.common.state.callback.CollectListener
import com.xiaoneng.ss.common.state.callback.LoginSuccessState
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
    var emptyJson = Gson().toJson(ArrayList<LoginResponse>())
    var userInfoJson: String by SPreference(Constant.USER_INFO, emptyJson)
    lateinit var userInfo: LoginResponse
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

    fun loginSuccess(response: LoginResponse) {
        // 改变 sharedPreferences   isLogin值
        isLogin = true
        mState = LoginState()
        userInfoJson = Gson().toJson(response)

    }

    fun getUserBean(): LoginResponse {
        val resultType = object : TypeToken<LoginResponse>() {}.type
        val gson = Gson()
        return gson.fromJson<LoginResponse>(userInfoJson, resultType)

    }

    fun logoutSuccess() {
        UserInfo.mState = LogoutState()
        // 清除 cookie、登录缓存
        var mCookie by SPreference("cookie", "")
        mCookie = ""
        isLogin = false
        LoginSuccessState.notifyLoginState("未登录", "--", null)
    }
}