package com.xiaoneng.ss.module.mine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
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
    fun logout() {
        initiateRequest(
            { mLogoutData.value = mRepository.logout() },
            loadState
        )
    }
}