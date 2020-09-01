package com.xiaoneng.ss.module.mine.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.account.model.LoginResponse
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StsTokenBean
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.mine.repository.MineRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class MineViewModel : BaseViewModel<MineRepository>() {
    val mLogoutData: MutableLiveData<Any> = MutableLiveData()
    val mAvatarData: MutableLiveData<String> = MutableLiveData()
    val mStsData: MutableLiveData<StsTokenResp> = MutableLiveData()
    val mUserInfoData: MutableLiveData<LoginResponse> = MutableLiveData()
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

    fun modifyUserInfo(bean:LoginResponse) {
        initiateRequest(
            { mUserInfoData.value = mRepository.modifyUserInfo(bean) },
            loadState
        )
    }

    fun upload(context: Context, bean: StsTokenBean) {
        initiateRequest(
            { uploadFile(context,bean) },
            loadState
        )
    }

    fun uploadFile(context: Context, bean: StsTokenBean) {

    }

    fun downloadFile(context: Context, bean: StsTokenBean) {
//        initiateRequest(
//            { mAvatarData.value = OssUtils.downloadFile(context, bean, "Burning", mDownloadFile(context)) },
//            loadState
//        )
    }
}