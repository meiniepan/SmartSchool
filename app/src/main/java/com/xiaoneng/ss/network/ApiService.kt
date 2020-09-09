package com.xiaoneng.ss.network

import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.school.model.PerformanceResponse
import com.xiaoneng.ss.module.school.model.TaskBean
import com.xiaoneng.ss.module.school.model.TaskResponse
import com.xiaoneng.ss.module.school.model.TimetableResponse
import com.xiaoneng.ss.network.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/25
 * Time: 20:41
 */

interface ApiService {


    /**
     * 学生登陆接口
     */
    @POST("/api/v1/user/login/sloginin")
    suspend fun onLogin1(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     *教师登录接口
     */
    @POST("/api/v1/user/login/eloginin")
    suspend fun onLogin2(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     * 家长登陆接口
     */
    @POST("/api/v1/user/login/ploginin")
    suspend fun onLogin3(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     * 学生登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/smsCode")
    suspend fun onSmsCode1(
        @Field("phone") phone: String
    ): BaseResponse<CaptchaResponse>

    /**
     * 教师登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/esmsCode")
    suspend fun onSmsCode2(
        @Field("phone") phone: String
    ): BaseResponse<CaptchaResponse>

    /**
     * 家长登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/pSmsCode")
    suspend fun onSmsCode3(
        @Field("phone") phone: String
    ): BaseResponse<CaptchaResponse>

    /**
     * 家长登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/bindSmsCode")
    suspend fun onSmsCode4(
        @Field("phone") phone: String,
        @Field("token") token: String
    ): BaseResponse<CaptchaResponse>

    /**
     * 学生注册绑定接口
     */
    @POST("/api/v1/user/login/register")
    suspend fun onStuRegister(
        @Body requestBody: RegisterReq
    ): BaseResponse<RegisterResponse>


    /**
     * 学生信息修改接口
     */
    @POST("/api/v1/user/student/modify")
    suspend fun modifyInfo1(
        @Body requestBody: UserBean
    ): BaseResponse<UserBean>

    /**
     * 教师信息修改接口
     */
    @POST("/api/v1/user/teachers/modify")
    suspend fun modifyInfo2(
        @Body requestBody: UserBean
    ): BaseResponse<UserBean>


    /**
     * 学生信息查询
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/info")
    suspend fun onQueryInfo1(
        @Field("token") token: String
    ): BaseResponse<UserBean>

    /**
     * 教师信息查询
     */
    @FormUrlEncoded
    @POST("/api/v1/user/teachers/info")
    suspend fun onQueryInfo2(
        @Field("token") token: String
    ): BaseResponse<UserBean>


    /**
     *学生获取授权应用列表
     */
    @POST("/api/v1/user/student/apps")
    @FormUrlEncoded
    suspend fun onStuApps(
        @Field("token") token: String
    ): BaseResponse<UserBean>

    /**
     * 教师获取授权应用列表
     */
    @FormUrlEncoded
    @POST("/api/v1/user/teachers/apps")
    suspend fun onTeaApps(
        @Field("token") token: String
    ): BaseResponse<UserBean>

    /**
     *学生通知列表
     */
    @FormUrlEncoded
    @POST("/api/v1/student/notices/lists")
    suspend fun getNoticeList(
        @Field("token") token: String,
        @Field("id") id: String,
        @Field("pagenum") pagenum: String = "",
        @Field("type") type: String = ""
    ): BaseResponse<NoticeResponse>

    /**
     *教师通知列表
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/notices/lists")
    suspend fun getNoticeList2(
        @Field("token") token: String,
        @Field("id") id: String,
        @Field("pagenum") pagenum: String = "",
        @Field("type") type: String = ""
    ): BaseResponse<NoticeResponse>

    /**
     *学生通知详情
     */
    @FormUrlEncoded
    @POST("/api/v1/student/notices/info")
    suspend fun getNoticeDetail(
        @Field("token") token: String,
        @Field("id") id: String
    ): BaseResponse<NoticeDetailBean>

    /**
     *教师通知详情
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/notices/info")
    suspend fun getNoticeDetail2(
        @Field("token") token: String,
        @Field("id") id: String
    ): BaseResponse<NoticeDetailBean>

    /**
     *学生通知状态变更
     */
    @FormUrlEncoded
    @POST("/api/v1/student/notices/modify")
    suspend fun readNotice(
        @Field("token") token: String,
        @Field("id") id: String,
        @Field("status") status: String = "",
        @Field("received") received: String = ""
    ): BaseResponse<Any>

    /**
     *教师通知状态变更
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/notices/modify")
    suspend fun readNotice2(
        @Field("token") token: String,
        @Field("id") id: String,
        @Field("status") status: String = "",
        @Field("received") received: String = ""
    ): BaseResponse<Any>

    /**
     *学生任务接口
     */
    @FormUrlEncoded
    @POST("/api/v1/student/tasks/lists")
    suspend fun getTaskList(
        @Field("token") token: String,
        @Field("id") id: String = "",
        @Field("pagenum") pagenum: String = "",
        @Field("type") type: String = "",
        @Field("status") status: String = ""
    ): BaseResponse<TaskResponse>

    /**
     *教师任务接口
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/tasks/lists")
    suspend fun getTaskList2(
        @Field("token") token: String,
        @Field("id") id: String = "",
        @Field("pagenum") pagenum: String = "",
        @Field("type") type: String = "",
        @Field("status") status: String = ""
    ): BaseResponse<TaskResponse>

    /**
     *学生课程表
     */
    @FormUrlEncoded
    @POST("/api/v1/student/courses/timeTable")
    suspend fun getTimetable(
        @Field("token") token: String,
        @Field("time") time: String = "",
        @Field("semesterid") semesterid: String = ""
    ): BaseResponse<TimetableResponse>

    /**
     *教师课程表
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/courses/timeTable")
    suspend fun getTimetable2(
        @Field("token") token: String,
        @Field("time") time: String = "",
        @Field("classid") classid: String = "",
        @Field("groupid") groupid: String = ""
    ): BaseResponse<TimetableResponse>

    /**
     *班主任课程表
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/courses/mTimeTable")
    suspend fun getTimetableMaster(
        @Field("token") token: String,
        @Field("time") time: String = "",
        @Field("classid") classid: String = ""
    ): BaseResponse<TimetableResponse>

    /**
     *学生查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v1/student/achievements/lists")
    suspend fun getPerformance(
        @Field("token") token: String,
        @Field("testname") testname: String ,
        @Field("crid") crid: String = ""
    ): BaseResponse<PerformanceResponse>

    /**
     *教师查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/achievements/lists")
    suspend fun getPerformance2(
        @Field("token") token: String,
        @Field("testname") testname: String ,
        @Field("crid") crid: String = ""
    ): BaseResponse<PerformanceResponse>

    /**
     *学生登录退出
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/sloginout")
    suspend fun logout(
        @Field("phone") phone: String,
        @Field("token") token: String
    ): BaseResponse<Any>

    /**
     *教师登录退出
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/eloginout")
    suspend fun logout2(
        @Field("phone") phone: String,
        @Field("token") token: String
    ): BaseResponse<Any>

    /**
     *学生查看考勤
     */
    @FormUrlEncoded
    @POST("/api/v1/student/attendances/lists")
    suspend fun getAttendance(
        @Field("token") token: String,
        @Field("classid") classid: String,
        @Field("groupid") groupid: String = "",
        @Field("teacheruid") teacheruid: String = "",
        @Field("atttime") atttime: String = ""
    ): BaseResponse<Any>

    /**
     *教师查看考勤
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/attendances/lists")
    suspend fun getAttendance2(
        @Field("token") token: String,
        @Field("classid") classid: String,
        @Field("groupid") groupid: String = "",
        @Field("teacheruid") teacheruid: String = "",
        @Field("atttime") atttime: String = ""
    ): BaseResponse<Any>

    /**
     *学生上传文件oss签名接口
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/osssts")
    suspend fun getSts(
        @Field("token") token: String
    ): BaseResponse<StsTokenResp>

    /**
     *教师上传文件oss签名接口
     */
    @FormUrlEncoded
    @POST("/api/v1/user/teachers/osssts")
    suspend fun getSts2(
        @Field("token") token: String
    ): BaseResponse<StsTokenResp>

    /**
     *app应用权限接口
     */
    @FormUrlEncoded
    @POST("/api/v1/admin/rbac/apps")
    suspend fun getAuthority(
        @Field("token") token: String,
        @Field("roleid") roleid: String
    ): BaseResponse<RegisterResponse>

    /**
     *管理查询所有学生
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/student/listAll")
    suspend fun queryStudent(
        @Field("token") token: String,
        @Field("key") key: String = ""
    ): BaseResponse<StudentResp>

    /**
     *学生查询日程
     */
    @FormUrlEncoded
    @POST("/api/v1/student/schedules/listDWM")
    suspend fun querySchedule(
        @Field("token") token: String,
        @Field("day") day: String,
        @Field("week_s") week_s: String = "",
        @Field("week_e") week_e: String = "",
        @Field("month") month: String = ""
    ): BaseResponse<Any>

    /**
     *教师查询日程
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/schedules/listDWM")
    suspend fun querySchedule2(
        @Field("token") token: String,
        @Field("day") day: String,
        @Field("week_s") week_s: String = "",
        @Field("week_e") week_e: String = "",
        @Field("month") month: String = ""
    ): BaseResponse<Any>

    /**
     *学生添加日程
     */
    @POST("/api/v1/student/schedules/add")
    suspend fun addSchedule(
        @Body requestBody: ScheduleBean
    ): BaseResponse<Any>

    /**
     *教师添加日程
     */
    @POST("/api/v1/teacher/schedules/add")
    suspend fun addSchedule2(
        @Body requestBody: ScheduleBean
    ): BaseResponse<Any>

    /**
     *学生修改日程
     */
    @POST("/api/v1/student/schedules/modify")
    suspend fun modifySchedule(
        @Body requestBody: ScheduleBean
    ): BaseResponse<Any>

    /**
     *教师修改日程
     */
    @POST("/api/v1/teacher/schedules/modify")
    suspend fun modifySchedule2(
        @Body requestBody: ScheduleBean
    ): BaseResponse<Any>

    /**
     *教师修改日程
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/departments/treeDep")
    suspend fun queryDepartments(
        @Field("token") token: String
    ): BaseResponse<Any>

    /**
     *学生发布任务审批
     */
    @FormUrlEncoded
    @POST("/api/v1/student/tasks/examine")
    suspend fun queryDepartments(
        @Field("token") token: String,
        @Field("id") id: String = "",
        @Field("examine") examine: String = "",
        @Field("examinestatus") examinestatus: String = ""
    ): BaseResponse<Any>

    /**
     *教师发布任务审批
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/tasks/examine")
    suspend fun examineTask2(
        @Field("token") token: String,
        @Field("id") id: String = "",
        @Field("examine") examine: String = "",
        @Field("examinestatus") examinestatus: String = ""
    ): BaseResponse<Any>

    /**
     *学生家长信息查询
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/pinfo")
    suspend fun getParents(
        @Field("token") token: String
    ): BaseResponse<Any>

    /**
     *学生绑定家长
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/pBind")
    suspend fun bindParent(
        @Field("token") token: String,
        @Field("phone") phone: String,
        @Field("vcode") vcode: String
    ): BaseResponse<Any>

    /**
     *学生解除绑定家长
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/pUnbind")
    suspend fun unbindParent(
        @Field("token") token: String,
        @Field("phone") phone: String
    ): BaseResponse<Any>

    /**
     *学生家长切换登录学生
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/modifyPS")
    suspend fun switchChild(
        @Field("token") token: String,
        @Field("uid") uid: String
    ): BaseResponse<Any>

    /**
     *教师查询班级邀请码
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/classs/codeList")
    suspend fun queryCodeList(
        @Field("token") token: String,
        @Field("classid") classid: String? = null
    ): BaseResponse<Any>

    /**
     *教师发布任务
     */
    @POST("/api/v1/teacher/tasks/add")
    suspend fun addTask(
        @Body requestBody: TaskBean
    ): BaseResponse<Any>
}