package com.xiaoneng.ss.module.school.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.model.AttendanceResponse
import com.xiaoneng.ss.module.school.model.PerformanceResponse
import com.xiaoneng.ss.module.school.model.TaskResponse
import com.xiaoneng.ss.module.school.model.TimetableResponse
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class SchoolRepository(val loadState: MutableLiveData<State>) : ApiRepository() {

    suspend fun getTaskList(pagenum: String, status: String): TaskResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTaskList(
                    UserInfo.getUserBean().token,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTaskList2(
                    UserInfo.getUserBean().token,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTaskList2(
                    UserInfo.getUserBean().token,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTaskList(
                    UserInfo.getUserBean().token,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getTimetable(classid: String = ""): TimetableResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTimetable(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTimetable2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTimetableMaster(UserInfo.getUserBean().token, classid = classid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTimetable(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }

        }

    }

    suspend fun getTimetableT(): TimetableResponse {
        return apiService.getTimetable2(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun getPerformance(crid: String): PerformanceResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getPerformance(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, crid)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, crid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getPerformance(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendance(classid: String): AttendanceResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAttendance(UserInfo.getUserBean().token, classid)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAttendance2(UserInfo.getUserBean().token, classid)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getAttendance2(UserInfo.getUserBean().token, classid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getAttendance(UserInfo.getUserBean().token, classid)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun queryStudent(key: String): StudentResp {
        return apiService.queryStudent(UserInfo.getUserBean().token, key)
            .dataConvert(loadState)
    }

    suspend fun queryDepartments(): Any {
        return apiService.queryDepartments(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

}