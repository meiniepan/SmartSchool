package com.xiaoneng.ss.module.mine.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class MineRepository(val loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun logout(): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.logout(UserInfo.getUserBean().phone,UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.logout2(UserInfo.getUserBean().phone,UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.logout(UserInfo.getUserBean().phone,UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
        }
    }
}