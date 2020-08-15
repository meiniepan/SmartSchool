package com.xiaoneng.ss.module.account.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.module.account.model.CaptchaResponse
import com.xiaoneng.ss.module.account.model.LoginResponse
import com.xiaoneng.ss.module.account.model.RegisterResponse
import com.xiaoneng.ss.module.account.repository.AccountRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:58
 */
class AccountViewModel : BaseViewModel<AccountRepository>() {


    // 使用协程 + Retrofit2.6以上版本
    val mLoginData: MutableLiveData<LoginResponse> = MutableLiveData()
    val mCaptchaData: MutableLiveData<CaptchaResponse> = MutableLiveData()
    val mRegisterData: MutableLiveData<RegisterResponse> = MutableLiveData()

    fun captcha(phone: String) {
        initiateRequest(
            { mCaptchaData.value = mRepository.captcha(phone) },
            loadState
        )
    }

    fun loginCo(phone: String, vcode: String = "", password: String = "") {
        initiateRequest(
            { mLoginData.value = mRepository.loginCo(phone, vcode, password) },
            loadState
        )
    }


    fun registerCo(username: String, password: String, repassword: String) {
        initiateRequest({
            mRegisterData.value = mRepository.registerCo(username, password, repassword)
        }, loadState)
    }
}
