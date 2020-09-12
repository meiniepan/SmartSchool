package com.xiaoneng.ss.module.circular.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:09
 */
class CircularRepository(val loadState: MutableLiveData<State>) : ApiRepository() {


    suspend fun getNoticeList(page: String, pagenum: String, type: String): NoticeResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum, type)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getNoticeList2(UserInfo.getUserBean().token, page, pagenum, type)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getNoticeList2(UserInfo.getUserBean().token, page, pagenum, type)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getNoticeList(UserInfo.getUserBean().token, page, pagenum, type)
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

    suspend fun read(id: String, status: String = "", received: String = ""): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.readNotice(UserInfo.getUserBean().token, id, status, received)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.readNotice2(UserInfo.getUserBean().token, id, status, received)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.readNotice2(UserInfo.getUserBean().token, id, status, received)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.readNotice(UserInfo.getUserBean().token, id, status, received)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun readAll(): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.readAll(UserInfo.getUserBean().token, type = "system")
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.readAllTea(UserInfo.getUserBean().token, type = "system")
                    .dataConvert(loadState)
            }
            "99" -> {
                apiService.readAllTea(UserInfo.getUserBean().token, type = "system")
                    .dataConvert(loadState)
            }
            else -> {
                apiService.readAllTea(UserInfo.getUserBean().token, type = "system")
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun querySchedule(day: String, month: String): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.querySchedule(UserInfo.getUserBean().token, day, month = month)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.querySchedule2(UserInfo.getUserBean().token, day, month = month)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.querySchedule2(UserInfo.getUserBean().token, day, month = month)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.querySchedule(UserInfo.getUserBean().token, day, month = month)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun addSchedule(bean: ScheduleBean): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.addSchedule(bean)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.addSchedule2(bean)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.addSchedule2(bean)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.addSchedule(bean)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun modifySchedule(bean: ScheduleBean): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.modifySchedule(bean)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.modifySchedule2(bean)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.modifySchedule2(bean)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.modifySchedule(bean)
                    .dataConvert(loadState)

            }
        }
    }
}