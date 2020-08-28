package com.xiaoneng.ss.module.circular.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
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
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getNoticeList2(UserInfo.getUserBean().token, page, pagenum)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getNoticeList2(UserInfo.getUserBean().token, page, pagenum)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getNoticeDetail(id: String): NoticeDetailBean {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getNoticeDetail(UserInfo.getUserBean().token, id)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getNoticeDetail2(UserInfo.getUserBean().token, id)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getNoticeDetail2(UserInfo.getUserBean().token, id)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getNoticeDetail(UserInfo.getUserBean().token, id)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun read(id: String,status:String): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.readNotice(UserInfo.getUserBean().token, id,status = status)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.readNotice2(UserInfo.getUserBean().token, id,status = status)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.readNotice2(UserInfo.getUserBean().token, id,status = status)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.readNotice(UserInfo.getUserBean().token, id,status = status)
                    .dataConvert(loadState)

            }
        }
    }
}