package com.xiaoneng.ss.module.circular.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class CircularRepository(val loadState: MutableLiveData<State>) : ApiRepository() {


    suspend fun getNoticeList(page: String, pagenum: String): NoticeResponse {
        if (UserInfo.getUserBean().usertype == "1") {
            return apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum).dataConvert(loadState)
        } else if (UserInfo.getUserBean().usertype == "2") {
            return apiService.getNoticeList2(UserInfo.getUserBean().token, page, pagenum).dataConvert(loadState)

        } else {
            return apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum).dataConvert(loadState)

        }
    }
}