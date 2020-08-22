package com.xiaoneng.ss.module.school.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.school.model.TaskResponse
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class SchoolRepository(val loadState: MutableLiveData<State>) : ApiRepository() {

    suspend fun getTaskList(pagenum: String): TaskResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTaskList(UserInfo.getUserBean().token,pagenum = pagenum)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTaskList2(UserInfo.getUserBean().token, pagenum = pagenum)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTaskList(UserInfo.getUserBean().token,pagenum = pagenum)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getTimetable(pagenum: String): TaskResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTimetable(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTimetable(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTimetable2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
        }
    }

}