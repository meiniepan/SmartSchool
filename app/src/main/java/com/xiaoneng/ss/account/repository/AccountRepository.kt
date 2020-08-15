package com.xiaoneng.ss.module.account.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.module.account.model.CaptchaResponse
import com.xiaoneng.ss.module.account.model.LoginReq
import com.xiaoneng.ss.module.account.model.LoginResponse
import com.xiaoneng.ss.module.account.model.RegisterResponse
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/03/01
 * Time: 19:59
 */
class AccountRepository(val loadState: MutableLiveData<State>) : ApiRepository() {
//    fun login(
//        username: String,
//        password: String,
//        liveData: MutableLiveData<BaseResponse<LoginResponse>>
//    ) {
//        apiService.onLogin(username, password)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                BaseObserver(
//                    liveData,
//                    loadState,
//                    this
//                )
//            )
//    }


    // 使用协程 + Retrofit2.6
    suspend fun loginCo(phone: String, vcode: String, password: String): LoginResponse {

        return apiService.onStuLogin(
            LoginReq(phone = phone,vcode = vcode,spassword = password)
        ).dataConvert(loadState)
//        return apiService.onTeaSmsCode(phone).dataConvert(loadState)

    }

    suspend fun captcha(phone: String): CaptchaResponse {
        return apiService.onStuSmsCode(phone).dataConvert(loadState)

    }

    suspend fun registerCo(
        username: String,
        password: String,
        repassword: String
    ): RegisterResponse {
        return apiService.onStuRegister(username, password,password,password, repassword).dataConvert(loadState)
    }
}