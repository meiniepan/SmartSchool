package com.xiaoneng.ss.account.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.account.repository.AccountRepository
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
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

    fun captchaTeacher(phone: String) {
        initiateRequest(
            { mCaptchaData.value = mRepository.captchaTeacher(phone) },
            loadState
        )
    }

    fun loginCo(requestBody: LoginReq) {
        initiateRequest(
            { mLoginData.value = mRepository.loginCo(requestBody) },
            loadState
        )
    }

    fun loginTeacher(requestBody: LoginReq) {
        initiateRequest(
            { mLoginData.value = mRepository.loginTeacher(requestBody) },
            loadState
        )
    }


    fun registerCo(requestBody: RegisterReq) {
        initiateRequest({
            mRegisterData.value = mRepository.registerCo(requestBody)
        }, loadState)
    }

    fun getAuthority() {
        initiateRequest({
            mRegisterData.value = mRepository.getAuthority()
        }, loadState)
    }
}
