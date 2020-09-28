package com.xiaoneng.ss.module.school.repository

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.repository.ApiRepository
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.model.StsTokenResp
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

    suspend fun getPublishTaskList(pagenum: String, status: String): TaskResponse {
        return apiService.getPublishTaskListTea(
            UserInfo.getUserBean().token,
            pagenum = pagenum,
            status = status
        )
            .dataConvert(loadState)

    }

    suspend fun getTimetable(classid: String = "", time: String = ""): TimetableResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTimetable(UserInfo.getUserBean().token, time = time)
                    .dataConvert(loadState)
            }
            "2" -> {
                if (UserInfo.getUserBean().classmaster == "1") {
                    apiService.getTimetableMaster(
                        UserInfo.getUserBean().token,
                        classid = classid,
                        time = time
                    )
                        .dataConvert(loadState)
                } else {
                    apiService.getTimetable2(UserInfo.getUserBean().token, time = time)
                        .dataConvert(loadState)
                }

            }
            "99" -> {
                apiService.getTimetableMaster(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    time = time
                )
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTimetable(UserInfo.getUserBean().token, time = time)
                    .dataConvert(loadState)

            }

        }

    }

    suspend fun getAttTimetable(time: String = "", uId: String = ""): Any {


        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAttTimetableStu(UserInfo.getUserBean().token, time = time, uid = uId)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAttTimetableTea(UserInfo.getUserBean().token, time = time, uid = uId)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getAttTimetableTea(UserInfo.getUserBean().token, time = time, uid = uId)
                    .dataConvert(loadState)

            }

        }

    }


    suspend fun getTestCourse(): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTestCourseStu(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTestCourseTea(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTestCourseTea(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTestCourseStu(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getPerformance(
        testname: String,
        crid: String,
        classid: String
    ): PerformanceResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getPerformance(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, testname, crid, classid)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getPerformance2(UserInfo.getUserBean().token, testname, crid, classid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getPerformance(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendance(
        classid: String? = null, atttime: String? = null,
        keyword: String? = null
    ): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAttendanceStuAdmin(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    atttime = atttime,
                    keyword = keyword
                )
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAttendanceTea(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    time = atttime,
                    keyword = keyword
                )
                    .dataConvert(loadState)

            }
            "99" -> {

                apiService.getAttendanceSchool(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    time = atttime
                )
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getAttendanceTea(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    time = atttime,
                    keyword = keyword
                )
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendanceStu(
        classid: String = "",
        atttime: String = ""
    ): Any {
        return apiService.getAttendance(
            UserInfo.getUserBean().token,
            classid,
            atttime = atttime
        )
            .dataConvert(loadState)
    }
    suspend fun getAttendanceInfo(
        id: String = ""
    ): Any {
        return apiService.getAttendanceInfo(
            UserInfo.getUserBean().token,
            id
        )
            .dataConvert(loadState)
    }


    suspend fun queryStudent(keyword: String): Any {
        return apiService.queryStudent(UserInfo.getUserBean().token, keyword)
            .dataConvert(loadState)
    }

    suspend fun queryDepartments(): Any {
        return apiService.queryDepartments(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun listByDepartment(id: String, realName: String): Any {
        return apiService.listByDepartment(
            UserInfo.getUserBean().token,
            depid = id,
            realname = realName
        )
            .dataConvert(loadState)
    }

    suspend fun addAttendanceByMaster(): Any {
        return apiService.queryDepartments(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun addTask(bean: TaskBean): Any {
        return apiService.addTask(bean)
            .dataConvert(loadState)
    }

    suspend fun deleteAttendance(id: String): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                if (UserInfo.getUserBean().usertype == "0") {

                } else {
                    apiService.deleteAttendanceByStu(UserInfo.getUserBean().token, id)
                        .dataConvert(loadState)
                }
            }
            "2" -> {
                if (UserInfo.getUserBean().classmaster == "1") {
                    apiService.deleteAttendanceByTea(UserInfo.getUserBean().token, id)
                        .dataConvert(loadState)
                } else {
                }
            }
            "99" -> {
                if (UserInfo.getUserBean().classmaster == "1") {
                    apiService.deleteAttendanceByTea(UserInfo.getUserBean().token, id)
                        .dataConvert(loadState)
                } else {
                }
            }
            else -> {
            }
        }
    }

    suspend fun addAttendance(bean: LeaveBean): Any {

        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                if (UserInfo.getUserBean().usertype == "0") {
                    apiService.addAttendance(bean)
                        .dataConvert(loadState)
                } else {
                    apiService.addAttendanceByStu(bean)
                        .dataConvert(loadState)
                }
            }
            else -> {
                apiService.addAttendanceByTea(bean)
                    .dataConvert(loadState)
            }
        }

    }

    suspend fun getSts(): StsTokenResp {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getSts(UserInfo.getUserBean().token)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getSts2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getSts2(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getSts(UserInfo.getUserBean().token)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getClassesByTea(): Any {
        return apiService.getClassesByTea(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun getStudentsByClass(classId: String? = null, realName: String? = null): Any {
        return apiService.getStudentsByClass(
            UserInfo.getUserBean().token,
            classid = classId, realname = realName
        )
            .dataConvert(loadState)
    }

    suspend fun getTaskInfo(id: String, type: String? = null): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTaskInfoStu(UserInfo.getUserBean().token, id)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTaskInfoTea(UserInfo.getUserBean().token, id, type)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTaskInfoTea(UserInfo.getUserBean().token, id, type)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTaskInfoTea(UserInfo.getUserBean().token, id, type)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun modifyTaskInfo(body: TaskLogRequest): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.modifyTaskInfoStu(body)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.modifyTaskInfoTea(body)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.modifyTaskInfoTea(body)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.modifyTaskInfoTea(body)
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun refuseTask(body: TaskLogRequest): Any {
        return apiService.refuseTask(body)
            .dataConvert(loadState)

    }

    suspend fun modifyTaskStatus(body: TaskBean): Any {
        return apiService.modifyTaskStatus(body)
            .dataConvert(loadState)

    }

    suspend fun delTaskDraft(id: String): Any {
        return apiService.delTaskDraft(UserInfo.getUserBean().token, id)
            .dataConvert(loadState)

    }

}