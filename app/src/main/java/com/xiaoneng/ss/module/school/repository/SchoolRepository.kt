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

    suspend fun getTaskList(
        pagenum: String? = null,
        status: String? = null,
        lastid: String? = null
    ): TaskResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getTaskList(
                    UserInfo.getUserBean().token,
                    id = lastid,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getTaskList2(
                    UserInfo.getUserBean().token,
                    id = lastid,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getTaskList2(
                    UserInfo.getUserBean().token,
                    id = lastid,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getTaskList(
                    UserInfo.getUserBean().token,
                    id = lastid,
                    pagenum = pagenum,
                    status = status
                )
                    .dataConvert(loadState)

            }
        }
    }

    suspend fun getPublishTaskList(
        pagenum: String? = null,
        status: String? = null,
        lastid: String? = null
    ): TaskResponse {
        return apiService.getPublishTaskListTea(
            UserInfo.getUserBean().token,
            id = lastid,
            pagenum = pagenum,
            status = status
        )
            .dataConvert(loadState)

    }

    suspend fun getTimetable(classid: String? = null, time: String? = null): TimetableResponse {
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

    suspend fun getAttTimetable(time: String? = null, uId: String? = null): Any {


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

    suspend fun getAchievement(
        testname: String? = null,
        crid: String? = null,
        classid: String? = null,
        lastid: String? = null
    ): AchievementResponse {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAchievement(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAchievement2(UserInfo.getUserBean().token,lastid, testname, crid, classid)
                    .dataConvert(loadState)

            }
            "99" -> {
                apiService.getAchievement2(UserInfo.getUserBean().token, lastid,testname, crid, classid)
                    .dataConvert(loadState)

            }
            else -> {
                apiService.getAchievement(UserInfo.getUserBean().token, testname)
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendance(
        classid: String? = null, courseId: String? = null, atttime: String? = null,
        keyword: String? = null
    ): Any {
        return when (UserInfo.getUserBean().usertype) {
            "1" -> {
                apiService.getAttendanceStuAdmin(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    courseid = courseId,
                    atttime = atttime,
                    keyword = keyword
                )
                    .dataConvert(loadState)
            }
            "2" -> {
                apiService.getAttendanceTea(
                    UserInfo.getUserBean().token,
                    classid = classid,
                    courseid = courseId,
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
                    courseid = courseId,
                    time = atttime,
                    keyword = keyword
                )
                    .dataConvert(loadState)
            }
        }
    }

    suspend fun getAttendanceStu(
        classid: String? = null,
        atttime: String? = null
    ): Any {
        return apiService.getAttendance(
            UserInfo.getUserBean().token,
            classid,
            atttime = atttime
        )
            .dataConvert(loadState)
    }

    suspend fun getAttendanceInfo(
        id: String? = null
    ): Any {
        return apiService.getAttendanceInfo(
            UserInfo.getUserBean().token,
            id
        )
            .dataConvert(loadState)
    }


    suspend fun queryStudent(keyword: String? = null): Any {
        return apiService.queryStudent(UserInfo.getUserBean().token, keyword)
            .dataConvert(loadState)
    }

    suspend fun queryDepartments(): Any {
        return apiService.queryDepartments(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

    suspend fun listByDepartment(id: String? = null, realName: String? = null): Any {
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

    suspend fun deleteAttendance(id: String? = null): Any {
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

    suspend fun getTaskInfo(id: String? = null, type: String? = null): Any {
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

    suspend fun delTaskDraft(id: String? = null): Any {
        return apiService.delTaskDraft(UserInfo.getUserBean().token, id)
            .dataConvert(loadState)

    }

    suspend fun getSalaryDetail(id: String?,code: String?): Any {
        return apiService.getSalaryDetail(UserInfo.getUserBean().token,id,code)
            .dataConvert(loadState)
    }

    suspend fun getSalaryList(page: String? = null): Any {
        return apiService.getSalaryList(UserInfo.getUserBean().token, page)
            .dataConvert(loadState)
    }

    suspend fun getSalaryCaptcha(): Any {
        return apiService.getSalaryCaptcha(UserInfo.getUserBean().token)
            .dataConvert(loadState)
    }

}