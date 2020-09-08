package com.xiaoneng.ss.module.school.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.network.dataConvert

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
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

    suspend fun getTimetable(classid: String = "",time: String = ""): TimetableResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTimetable(UserInfo.getUserBean().token,time = time)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTimetable2(UserInfo.getUserBean().token,time = time)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTimetableMaster(UserInfo.getUserBean().token, classid = classid,time = time)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTimetable(UserInfo.getUserBean().token,time = time)
                    .dataConvert(loadState)

            }

        }

    }

    suspend fun getTimetableT(): TimetableResponse {
        return apiService.getTimetable2(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun getPerformance(testname: String, crid: String): PerformanceResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getPerformance(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, testname, crid)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, testname, crid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getPerformance(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendance(classid: String = "",atttime:String = ""): AttendanceResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAttendance(UserInfo.getUserBean().token, classid,atttime = atttime)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAttendance2(UserInfo.getUserBean().token, classid,atttime = atttime)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getAttendance2(UserInfo.getUserBean().token, classid,atttime = atttime)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getAttendance(UserInfo.getUserBean().token, classid,atttime = atttime)
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

    suspend fun addAttendanceByMaster(): Any {
        return apiService.queryDepartments(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun addTask(bean:TaskBean): Any {
        return apiService.addTask(bean)
            .dataConvert(loadState)
    }

}