package com.xiaoneng.ss.account.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.account.repository.AccountRepository
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StsTokenResp
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
    val mLoginData: MutableLiveData<UserBean> = MutableLiveData()
    val mCaptchaData: MutableLiveData<CaptchaResponse> = MutableLiveData()
    val mRegisterData: MutableLiveData<RegisterResponse> = MutableLiveData()

    val mLogoutData: MutableLiveData<Any> = MutableLiveData()
    val mAvatarData: MutableLiveData<UserBean> = MutableLiveData()
    val mStsData: MutableLiveData<StsTokenResp> = MutableLiveData()
    val mUserInfoData: MutableLiveData<UserBean> = MutableLiveData()
    val mParentNameData: MutableLiveData<Any> = MutableLiveData()
    val mParentsData: MutableLiveData<Any> = MutableLiveData()
    val mChildData: MutableLiveData<Any> = MutableLiveData()
    val mBaseData: MutableLiveData<Any> = MutableLiveData()
    val mVerifyData: MutableLiveData<Any> = MutableLiveData()

    fun captcha(type: Int, phone: String) {
        initiateRequest(
            { mCaptchaData.value = mRepository.captcha(type, phone) },
            loadState
        )
    }


    fun login(type: Int, requestBody: LoginReq) {
        initiateRequest(
            { mLoginData.value = mRepository.login(type, requestBody) },
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


    fun logout() {
        initiateRequest(
            { mLogoutData.value = mRepository.logout() },
            loadState
        )
    }

    fun getSts() {
        initiateRequest(
            { mStsData.value = mRepository.getSts() },
            loadState
        )
    }

    fun modifyAvatar(bean: UserBean) {
        initiateRequest(
            { mAvatarData.value = mRepository.modifyUserInfo(bean) },
            loadState
        )
    }

    fun modifyUserInfo(bean: UserBean) {
        initiateRequest(
            { mUserInfoData.value = mRepository.modifyUserInfo(bean) },
            loadState
        )
    }
    fun modifyParentName(name: String) {
        initiateRequest(
            { mParentNameData.value = mRepository.modifyParentName(name) },
            loadState
        )
    }



    fun getParents() {
        initiateRequest(
            { mParentsData.value = mRepository.getParents() },
            loadState
        )
    }

    fun bindParent(phone: String, vcode: String) {
        initiateRequest(
            { mParentsData.value = mRepository.bindParent(phone, vcode) },
            loadState
        )
    }

    fun unbindParent(phone: String) {
        initiateRequest(
            { mParentsData.value = mRepository.unbindParent(phone) },
            loadState
        )
    }

    fun switchChild(uid: String) {
        initiateRequest(
            { mChildData.value = mRepository.switchChild(uid) },
            loadState
        )
    }

    fun queryCodeList(classId: String? = null) {
        initiateRequest(
            { mParentsData.value = mRepository.queryCodeList(classId) },
            loadState
        )
    }

    fun onSmsCodeChange(phone: String) {
        initiateRequest(
            { mBaseData.value = mRepository.onSmsCodeChange(phone) },
            loadState
        )
    }

    fun verifyVcode(phone: String, vCode: String) {
        initiateRequest(
            { mVerifyData.value = mRepository.changePhone(phone,vCode) },
            loadState
        )
    }
}
