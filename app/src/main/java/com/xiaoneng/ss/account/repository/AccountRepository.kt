package com.xiaoneng.ss.module.account.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.observer.BaseObserver
import com.xiaoneng.ss.network.response.BaseResponse
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.network.dataConvert
import com.xiaoneng.ss.module.account.model.LoginResponse
import com.xiaoneng.ss.module.account.model.RegisterResponse
import com.xiaoneng.ss.network.StudentInfoReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
//        var map = HashMap<String,String>()
//        map.put("token","66f9f08f34d84500a3d6d5334b87da36")
//        map.put("realname","haha")
//        return apiService.onStuModifyInfo(map).dataConvert(loadState)
        return apiService.onTeaSmsCode(phone).dataConvert(loadState)

    }

    suspend fun registerCo(
        username: String,
        password: String,
        repassword: String
    ): RegisterResponse {
        return apiService.onStuRegister(username, password,password,password, repassword).dataConvert(loadState)
    }
}