package com.xiaoneng.ss.common.state

import android.app.Activity
import android.content.Context
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
    var token: String by SPreference(Constant.TOKEN, "")
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

    fun loginSuccess(token:String) {
        // 改变 sharedPreferences   isLogin值
        isLogin = true
        mState = LoginState()
        this.token = token

        // 登录成功 回调 -> DrawerLayout -> 个人信息更新状态
//        LoginSuccessState.notifyLoginState(username, userId, collectIds)
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