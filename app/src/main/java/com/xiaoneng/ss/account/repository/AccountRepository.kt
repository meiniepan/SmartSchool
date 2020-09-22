package com.xiaoneng.ss.account.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.model.StsTokenResp
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

    suspend fun login(type:Int,requestBody: LoginReq): UserBean {
        return when (type) {
            1 -> {
                apiService.onLogin1(requestBody)
                    .dataConvert(loadState)
            }
            2 -> {
                apiService.onLogin2(requestBody)
                    .dataConvert(loadState)

            }
            99 -> {
                apiService.onLogin2(requestBody)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.onLogin3(requestBody)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun captcha(type:Int,phone: String): CaptchaResponse {
        return when (type) {
            1 -> {
                apiService.onSmsCode1(phone)
                    .dataConvert(loadState)
            }
            2 -> {
                apiService.onSmsCode2(phone)
                    .dataConvert(loadState)

            }
            3 -> {
                apiService.onSmsCode3(phone)
                    .dataConvert(loadState)

            }
            Constant.BIND_PARENT_SMS -> {
                apiService.onSmsCode4(phone,UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            99 -> {
                apiService.onSmsCode2(phone)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.onSmsCode3(phone)
                    .dataConvert(loadState)
            }
        }
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

    suspend fun logout(): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.logout(UserInfo.getUserBean().phone, UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.logout2(UserInfo.getUserBean().phone, UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.logout2(UserInfo.getUserBean().phone, UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.logout(UserInfo.getUserBean().phone, UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getSts(): StsTokenResp {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getSts(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getSts2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getSts2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getSts(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun modifyUserInfo(bean: UserBean): UserBean {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.modifyInfo1(bean)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.modifyInfo2(bean)
                    .dataConvert(loadState)
            }
            "99" -> {
                apiService.modifyInfo2(bean)
                    .dataConvert(loadState)
            }
            else -> {
                apiService.modifyInfo1(bean)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun modifyParentName(name: String): Any {
        return apiService.modifyParentName(UserInfo.getUserBean().token,name)
                    .dataConvert(loadState)
    }

    suspend fun onSmsCodeChange(phone: String): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.onSmsCodeChangeStu(UserInfo.getUserBean().token,phone)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.onSmsCodeChangeTea(UserInfo.getUserBean().token,phone)
                    .dataConvert(loadState)
            }
            "99" -> {
                apiService.onSmsCodeChangeTea(UserInfo.getUserBean().token,phone)
                    .dataConvert(loadState)
            }
            else -> {

            }
        }
    }

    suspend fun changePhone(phone: String,vCode: String): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.changePhoneStu(UserInfo.getUserBean().token,phone,vCode)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.changePhoneTea(UserInfo.getUserBean().token,phone,vCode)
                    .dataConvert(loadState)
            }
            "99" -> {
                apiService.changePhoneTea(UserInfo.getUserBean().token,phone,vCode)
                    .dataConvert(loadState)
            }
            else -> {

            }
        }
    }

    suspend fun getParents(): Any {
        return apiService.getParents(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun bindParent(phone: String, vcode: String): Any {
        return apiService.bindParent(UserInfo.getUserBean().token, phone, vcode)
            .dataConvert(loadState)
    }

    suspend fun unbindParent(phone: String): Any {
        return apiService.unbindParent(UserInfo.getUserBean().token, phone)
            .dataConvert(loadState)
    }
    suspend fun switchChild(uid: String): Any {
        return apiService.switchChild(UserInfo.getUserBean().token, uid)
            .dataConvert(loadState)
    }
    suspend fun queryCodeList(classId: String?): Any {
        return apiService.queryCodeList(UserInfo.getUserBean().token, classId)
            .dataConvert(loadState)
    }
}