package com.xiaoneng.ss.network

import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.school.model.AttendanceResponse
import com.xiaoneng.ss.module.school.model.PerformanceResponse
import com.xiaoneng.ss.module.school.model.TaskResponse
import com.xiaoneng.ss.module.school.model.TimetableResponse
import com.xiaoneng.ss.network.response.BaseResponse
import retrofit2.http.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/25
 * Time: 20:41
 */

interface ApiService {
    /**
     * 学生登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/smsCode")
    suspend fun onStuSmsCode(
        @Field("phone") phone: String
    ): BaseResponse<CaptchaResponse>


    /**
     * 学生登陆接口
     */
    @POST("/api/v1/user/login/sloginin")
    suspend fun onStuLogin(
        @Body requestBody: LoginReq
    ): BaseResponse<LoginResponse>


    /**
     * 学生注册绑定接口
     */
    @POST("/api/v1/user/login/register")
    suspend fun onStuRegister(
        @Body requestBody: RegisterReq
    ): BaseResponse<RegisterResponse>


    /**
     * 学生信息查询
     */
    @FormUrlEncoded
    @POST("/api/v1/user/student/info")
    suspend fun onStuInfo(
        @Field("token") token: String
    ): BaseResponse<LoginResponse>


    /**
     * 学生信息修改接口
     */
    @POST("/api/v1/user/student/modify")
    suspend fun onStuModifyInfo(
        @Body requestBody: StudentInfoReq
    ): BaseResponse<LoginResponse>


    /**
     *学生获取授权应用列表
     */
    @POST("/api/v1/user/student/apps")
    @FormUrlEncoded
    suspend fun onStuApps(
        @Field("token") token: String
    ): BaseResponse<LoginResponse>


    /**
     *教师登录接口
     */
    @POST("/api/v1/user/login/eloginin")
    suspend fun onTeaLogin(
        @Body requestBody: LoginReq
    ): BaseResponse<LoginResponse>


    /**
     * 教师登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/esmsCode")
    suspend fun onTeaSmsCode(
        @Field("phone") phone: String
    ): BaseResponse<CaptchaResponse>


    /**
     * 教师信息查询
     */
    @FormUrlEncoded
    @POST("/api/v1/user/teachers/info")
    suspend fun onTeaInfo(
        @Field("token") token: String
    ): BaseResponse<LoginResponse>

    /**
     * 教师信息修改接口
     */
    @POST("/api/v1/user/teachers/modify")
    @FormUrlEncoded
    suspend fun onTeaModifyInfo(
        @FieldMap requestBody: HashMap<String, String>
    ): BaseResponse<LoginResponse>


    /**
     * 教师登录退出
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/eloginout")
    suspend fun onTeaLoginOut(
        @Field("phone") phone: String,
        @Field("token") token: String
    ): BaseResponse<LoginResponse>

    /**
     * 学生登录退出
     */
    @FormUrlEncoded
    @POST("/api/v1/user/login/loginout")
    suspend fun onStuLoginOut(
        @Field("phone") phone: String,
        @Field("token") token: String
    ): BaseResponse<LoginResponse>


    /**
     * 教师获取授权应用列表
     */
    @FormUrlEncoded
    @POST("/api/v1/user/teachers/apps")
    suspend fun onTeaApps(
        @Field("token") token: String
    ): BaseResponse<LoginResponse>

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
        @Field("id") id: String ,
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
        @Field("type") type: String = ""
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
        @Field("type") type: String = ""
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
     *学生查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v1/student/achievements/lists")
    suspend fun getPerformance(
        @Field("token") token: String,
        @Field("crid") crid: String = ""
    ): BaseResponse<PerformanceResponse>

    /**
     *教师查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/achievements/lists")
    suspend fun getPerformance2(
        @Field("token") token: String,
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
    ): BaseResponse<AttendanceResponse>
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
    ): BaseResponse<AttendanceResponse>

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
}