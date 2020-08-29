package com.xiaoneng.ss.account.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
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
    suspend fun loginCo(requestBody: LoginReq): LoginResponse {

        return apiService.onStuLogin(
            requestBody
        ).dataConvert(loadState)

    }

    suspend fun loginTeacher(requestBody: LoginReq): LoginResponse {

        return apiService.onTeaLogin(
            requestBody
        ).dataConvert(loadState)

    }

    suspend fun captcha(phone: String): CaptchaResponse {
        return apiService.onStuSmsCode(phone).dataConvert(loadState)

    }

    suspend fun captchaTeacher(phone: String): CaptchaResponse {
        return apiService.onTeaSmsCode(phone).dataConvert(loadState)

    }

    suspend fun registerCo(
        requestBody: RegisterReq
    ): RegisterResponse {
        return apiService.onStuRegister(requestBody).dataConvert(loadState)
    }

    suspend fun getAuthority(): RegisterResponse {
        return apiService.getAuthority(UserInfo.getUserBean().token,
            UserInfo.getUserBean().roleid
        ).dataConvert(loadState)
    }
}