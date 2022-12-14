package com.xiaoneng.ss.network

import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.school.model.*
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
    @POST("/api/v17/user/login/sloginin")
    suspend fun onLogin1(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     *教师登录接口
     */
    @POST("/api/v17/user/login/eloginin")
    suspend fun onLogin2(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     * 家长登陆接口
     */
    @POST("/api/v17/user/login/ploginin")
    suspend fun onLogin3(
        @Body requestBody: LoginReq
    ): BaseResponse<UserBean>

    /**
     * 学生登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/login/smsCode")
    suspend fun onSmsCode1(
        @Field("phone") phone: String?
    ): BaseResponse<CaptchaResponse>

    /**
     * 教师登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/login/esmsCode")
    suspend fun onSmsCode2(
        @Field("phone") phone: String?
    ): BaseResponse<CaptchaResponse>

    /**
     * 家长登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/login/pSmsCode")
    suspend fun onSmsCode3(
        @Field("phone") phone: String?
    ): BaseResponse<CaptchaResponse>

    /**
     * 家长登录获取短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/bindSmsCode")
    suspend fun onSmsCode4(
        @Field("phone") phone: String? = null,
        @Field("token") token: String?
    ): BaseResponse<CaptchaResponse>

    /**
     * 学生注册绑定接口
     */
    @POST("/api/v17/user/login/register")
    suspend fun onStuRegister(
        @Body requestBody: RegisterReq
    ): BaseResponse<RegisterResponse>


    /**
     * 学生信息修改接口
     */
    @POST("/api/v17/user/student/modify")
    suspend fun modifyInfo1(
        @Body requestBody: UserBean
    ): BaseResponse<UserBean>

    /**
     * 教师信息修改接口
     */
    @POST("/api/v17/user/teachers/modify")
    suspend fun modifyInfo2(
        @Body requestBody: UserBean
    ): BaseResponse<UserBean>

    /**
     * 学生家长姓名修改
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/modifyParents")
    suspend fun modifyParentName(
        @Field("token") token: String? = null,
        @Field("realname") realname: String?
    ): BaseResponse<Any>


    /**
     * 学生信息查询
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/info")
    suspend fun onQueryInfo1(
        @Field("token") token: String?
    ): BaseResponse<UserBean>

    /**
     * 教师信息查询
     */
    @FormUrlEncoded
    @POST("/api/v17/user/teachers/info")
    suspend fun onQueryInfo2(
        @Field("token") token: String?
    ): BaseResponse<UserBean>


    /**
     *学生获取授权应用列表
     */
    @POST("/api/v17/user/student/apps")
    @FormUrlEncoded
    suspend fun getAppsStu(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     * 教师获取授权应用列表
     */
    @FormUrlEncoded
    @POST("/api/v17/user/teachers/apps")
    suspend fun getAppsTea(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *学生通知列表
     */
    @FormUrlEncoded
    @POST("/api/v17/student/notices/lists")
    suspend fun getNoticeList(
        @Field("token") token: String? = null,
        @Field("id") lastid: String? = null,
        @Field("pagenum") pagenum: String? = null,
        @Field("type") type: String? = null
    ): BaseResponse<NoticeResponse>

    /**
     *通知与通知人关系列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/noticesrs/lists")
    suspend fun getNoticerList(
        @Field("token") token: String? = null,
        @Field("id") lastid: String? = null,
        @Field("type") type: String? = null,
        @Field("status") status: String? = null,
        @Field("noticeid") noticeid: String? = null
    ): BaseResponse<Any>

    /**
     *教师通知列表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/notices/lists")
    suspend fun getNoticeList2(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("pagenum") pagenum: String? = null,
        @Field("type") type: String? = null
    ): BaseResponse<NoticeResponse>

    /**
     *学生通知详情
     */
    @FormUrlEncoded
    @POST("/api/v17/student/notices/info")
    suspend fun getNoticeDetail(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<NoticeDetailBean>

    /**
     *教师通知详情
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/notices/info")
    suspend fun getNoticeDetail2(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<NoticeDetailBean>

    /**
     *学生通知状态变更
     */
    @FormUrlEncoded
    @POST("/api/v17/student/notices/modify")
    suspend fun readNotice(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("status") status: String? = null,
        @Field("received") received: String? = null
    ): BaseResponse<Any>


    /**
     *教师通知状态变更
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/notices/modify")
    suspend fun readNotice2(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("status") status: String? = null,
        @Field("received") received: String? = null
    ): BaseResponse<Any>

    /**
     *学生通知全部已读
     */
    @FormUrlEncoded
    @POST("/api/v17/student/notices/modifyAll")
    suspend fun readAll(
        @Field("token") token: String? = null,
        @Field("status") status: String? = "1",
        @Field("received") received: String? = null,
        @Field("type") type: String?
    ): BaseResponse<Any>

    /**
     *教师通知全部已读
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/notices/modifyAll")
    suspend fun readAllTea(
        @Field("token") token: String? = null,
        @Field("status") status: String? = null,
        @Field("received") received: String? = null,
        @Field("type") type: String?
    ): BaseResponse<Any>

    /**
     *学生任务接口
     */
    @FormUrlEncoded
    @POST("/api/v17/student/tasks/lists")
    suspend fun getTaskList(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("pagenum") pagenum: String? = null,
        @Field("type") type: String? = null,
        @Field("completestatus") status: String? = null,
        @Field("stime") stime: String? = null,
        @Field("etime") etime: String? = null
    ): BaseResponse<TaskResponse>

    /**
     *教师任务接口
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/tasks/lists")
    suspend fun getTaskList2(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("pagenum") pagenum: String? = null,
        @Field("type") type: String? = null,
        @Field("completestatus") status: String? = null,
        @Field("stime") stime: String? = null,
        @Field("etime") etime: String? = null
    ): BaseResponse<TaskResponse>

    /**
     *教师发布任务查询列表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/tasks/tasklist")
    suspend fun getPublishTaskListTea(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("pagenum") pagenum: String? = null,
        @Field("type") type: String? = null,
        @Field("status") status: String? = null,
        @Field("stime") stime: String? = null,
        @Field("etime") etime: String? = null
    ): BaseResponse<TaskResponse>

    /**
     *学生添加考勤-查看课程表
     */
    @FormUrlEncoded
    @POST("/api/v17/student/attendances/timeTable")
    suspend fun getAttTimetableStu(
        @Field("token") token: String? = null,
        @Field("time") time: String? = null,
        @Field("uid") uid: String? = null
    ): BaseResponse<Any>

    /**
     *班主任/任课教师添加考勤-查看课程表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/attendances/timeTable")
    suspend fun getAttTimetableTea(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("time") time: String? = null,
        @Field("uid") uid: String? = null
    ): BaseResponse<Any>

    /**
     *学生查看成绩-考试/班级/群组/课程信息
     */
    @FormUrlEncoded
    @POST("/api/v17/student/achievements/testCourse")
    suspend fun getTestCourseStu(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *教师查看成绩-考试/班级/群组/课程信息
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/achievements/testCourse")
    suspend fun getTestCourseTea(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *学生课程表
     */
    @FormUrlEncoded
    @POST("/api/v17/student/courses/timeTable")
    suspend fun getTimetable(
        @Field("token") token: String? = null,
        @Field("time") time: String? = null,
        @Field("semesterid") semesterid: String? = null
    ): BaseResponse<TimetableResponse>

    /**
     *教师课程表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/courses/timeTable")
    suspend fun getTimetable2(
        @Field("token") token: String? = null,
        @Field("time") time: String? = null,
        @Field("classid") classid: String? = null,
        @Field("groupid") groupid: String? = null
    ): BaseResponse<TimetableResponse>

    /**
     *班主任课程表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/courses/mTimeTable")
    suspend fun getTimetableMaster(
        @Field("token") token: String? = null,
        @Field("time") time: String? = null,
        @Field("classid") classid: String? = null
    ): BaseResponse<TimetableResponse>

    /**
     *学生查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v17/student/achievements/lists")
    suspend fun getAchievement(
        @Field("token") token: String? = null,
        @Field("testname") testname: String? = null,
        @Field("cno") crsid: String? = null
    ): BaseResponse<AchievementResponse>

    /**
     *教师信息查询接口
     */
    @FormUrlEncoded
    @POST("/api/v17/develop/teachers/info")
    suspend fun getArchives(
        @Field("token") token: String? = null,
        @Field("uid") uid: String? = null
    ): BaseResponse<Any>

    /**
     *教师查看成绩
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/achievements/lists")
    suspend fun getAchievement2(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("testname") testname: String? = null,
        @Field("cno") crsid: String? = null,
        @Field("classid") classid: String?
    ): BaseResponse<AchievementResponse>

    /**
     *学生登录退出
     */
    @FormUrlEncoded
    @POST("/api/v17/user/login/sloginout")
    suspend fun logout(
        @Field("phone") phone: String? = null,
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *教师登录退出
     */
    @FormUrlEncoded
    @POST("/api/v17/user/login/eloginout")
    suspend fun logout2(
        @Field("phone") phone: String? = null,
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *学生查看考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/student/attendances/privateAtts")
    suspend fun getAttendance(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("groupid") groupid: String? = null,
        @Field("teacheruid") teacheruid: String? = null,
        @Field("atttime") atttime: String? = null
    ): BaseResponse<Any>

    /**
     *学生考勤员查看考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/student/attendances/lists")
    suspend fun getAttendanceStuAdmin(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("courseid") courseid: String? = null,
        @Field("keyword") keyword: String? = null,
        @Field("atttime") atttime: String? = null
    ): BaseResponse<Any>

    /**
     *教师查看考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/attendances/lists")
    suspend fun getAttendanceTea(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("courseid") courseid: String? = null,
        @Field("groupid") groupid: String? = null,
        @Field("teacheruid") teacheruid: String? = null,
        @Field("keyword") keyword: String? = null,
        @Field("atttime") time: String? = null
    ): BaseResponse<Any>

    /**
     *班主任/教师查看考勤信息
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/attendances/info")
    suspend fun getAttendanceInfo(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *管理员校级考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/attendances/sclists")
    suspend fun getAttendanceSchool(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("level") level: String? = null,
        @Field("type") type: String? = null,//0按日统计1按学年统计
        @Field("time") time: String? = null
    ): BaseResponse<Any>

    /**
     *学生上传文件oss签名接口
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/osssts")
    suspend fun getSts(
        @Field("token") token: String?
    ): BaseResponse<StsTokenResp>

    /**
     *教师上传文件oss签名接口
     */
    @FormUrlEncoded
    @POST("/api/v17/user/teachers/osssts")
    suspend fun getSts2(
        @Field("token") token: String?
    ): BaseResponse<StsTokenResp>

    /**
     *app应用权限接口
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/rbac/apps")
    suspend fun getAuthority(
        @Field("token") token: String? = null,
        @Field("roleid") roleid: String?
    ): BaseResponse<RegisterResponse>

    /**
     *管理查询所有学生
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/student/listAll")
    suspend fun queryStudent(
        @Field("token") token: String? = null,
        @Field("keyword") keyword: String? = null
    ): BaseResponse<Any>

    /**
     *学生查询日程
     */
    @FormUrlEncoded
    @POST("/api/v17/student/schedules/listDWM")
    suspend fun querySchedule(
        @Field("token") token: String? = null,
        @Field("day") day: String? = null,
        @Field("week_s") week_s: String? = null,
        @Field("week_e") week_e: String? = null,
        @Field("month") month: String? = null
    ): BaseResponse<Any>

    /**
     *教师查询日程
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/schedules/listDWM")
    suspend fun querySchedule2(
        @Field("token") token: String? = null,
        @Field("day") day: String? = null,
        @Field("week_s") week_s: String? = null,
        @Field("week_e") week_e: String? = null,
        @Field("month") month: String? = null
    ): BaseResponse<Any>

    /**
     *学生添加日程
     */
    @POST("/api/v17/student/schedules/add")
    suspend fun addSchedule(
        @Body requestBody: ScheduleBean?
    ): BaseResponse<Any>

    /**
     *教师添加日程
     */
    @POST("/api/v17/teacher/schedules/add")
    suspend fun addSchedule2(
        @Body requestBody: ScheduleBean?
    ): BaseResponse<Any>

    /**
     *学生修改日程
     */
    @POST("/api/v17/student/schedules/modify")
    suspend fun modifySchedule(
        @Body requestBody: ScheduleBean?
    ): BaseResponse<Any>

    /**
     *教师修改日程
     */
    @POST("/api/v17/teacher/schedules/modify")
    suspend fun modifySchedule2(
        @Body requestBody: ScheduleBean?
    ): BaseResponse<Any>

    /**
     *学生删除个人日程
     */
    @FormUrlEncoded
    @POST("/api/v17/student/schedules/del")
    suspend fun deleteScheduleStu(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null
    ): BaseResponse<Any>

    /**
     *教师删除个人日程
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/schedules/del")
    suspend fun deleteScheduleTea(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null
    ): BaseResponse<Any>

    /**
     *教师获取组织架构
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/departments/treeDep")
    suspend fun queryDepartments(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *教师按部门查询教师接口
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/teacher/listByDep")
    suspend fun listByDepartment(
        @Field("token") token: String? = null,
        @Field("depid") depid: String? = null,
        @Field("realname") realname: String? = null
    ): BaseResponse<Any>

    /**
     *教师按班级获取学生列表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/student/listByClass")
    suspend fun getStudentsByClass(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null,
        @Field("realname") realname: String? = null,
        @Field("isall") isall: String? = null,
    ): BaseResponse<Any>

    /**
     *学生发布任务审批
     */
    @FormUrlEncoded
    @POST("/api/v17/student/tasks/examine")
    suspend fun queryDepartments(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("examine") examine: String? = null,
        @Field("examinestatus") examinestatus: String? = null
    ): BaseResponse<Any>

    /**
     *教师发布任务审批
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/tasks/examine")
    suspend fun examineTask2(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("examine") examine: String? = null,
        @Field("examinestatus") examinestatus: String? = null
    ): BaseResponse<Any>

    /**
     *学生家长信息查询
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/pinfo")
    suspend fun getParents(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *学生绑定家长
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/pBind")
    suspend fun bindParent(
        @Field("token") token: String? = null,
        @Field("phone") phone: String? = null,
        @Field("vcode") vcode: String?
    ): BaseResponse<Any>

    /**
     *学生解除绑定家长
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/pUnbind")
    suspend fun unbindParent(
        @Field("token") token: String? = null,
        @Field("phone") phone: String?
    ): BaseResponse<Any>

    /**
     *学生家长切换登录学生
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/modifyPS")
    suspend fun switchChild(
        @Field("token") token: String? = null,
        @Field("uid") uid: String?
    ): BaseResponse<Any>

    /**
     *教师查询班级邀请码
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/classs/codeList")
    suspend fun queryCodeList(
        @Field("token") token: String? = null,
        @Field("classid") classid: String? = null
    ): BaseResponse<Any>

    /**
     *教师发布任务
     */
    @POST("/api/v17/teacher/tasks/add")
    suspend fun addTask(
        @Body requestBody: TaskBean
    ): BaseResponse<Any>

    /**
     *学生考勤员删除考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/student/attendances/del")
    suspend fun deleteAttendanceByStu(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *教师删除考勤
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/attendances/del")
    suspend fun deleteAttendanceByTea(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *学生提交请假申请
     */
    @POST("/api/v17/student/attendances/add")
    suspend fun addAttendance(
        @Body requestBody: LeaveBean
    ): BaseResponse<Any>

    /**
     *学生考勤员添加考勤
     */
    @POST("/api/v17/student/attendances/mAdd")
    suspend fun addAttendanceByStu(
        @Body requestBody: LeaveBean
    ): BaseResponse<Any>

    /**
     *老师考勤员添加考勤
     */
    @POST("/api/v17/teacher/attendances/add")
    suspend fun addAttendanceByTea(
        @Body requestBody: LeaveBean
    ): BaseResponse<Any>

    /**
     *增加德育评比评分
     */
    @POST("/api/v17/moral/moralScore/add")
    suspend fun addMoralScore(
        @Body requestBody: QuantizeBody
    ): BaseResponse<Any>

    /**
     *增加德育评比特殊情况
     */
    @POST("/api/v17/moral/moralRuleSpecial/add")
    suspend fun addMoralScoreSpecial(
        @Body requestBody: QuantizeBody
    ): BaseResponse<Any>

    /**
     *教师获取班级
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/classs/treeClass")
    suspend fun getClassesByTea(
        @Field("token") token: String?,
        @Field("isall") isall: String?=null,
    ): BaseResponse<Any>

    /**
     *学生列表德育评比评分
     */
    @FormUrlEncoded
    @POST("/api/v17/moral/moralScore/slists")
    suspend fun getQuantizeListCommon(
        @Field("token") token: String?,
        @Field("typeid") typeid: String?
    ): BaseResponse<Any>

    /**
     *学生列表德育评比特殊情况
     */
    @FormUrlEncoded
    @POST("/api/v17/moral/moralRuleSpecial/slists")
    suspend fun getQuantizeListSpecial(
        @Field("token") token: String?
    ): BaseResponse<Any>



    /**
     *学生任务详情
     */
    @FormUrlEncoded
    @POST("/api/v17/student/tasks/info")
    suspend fun getTaskInfoStu(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *教师任务详情
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/tasks/info")
    suspend fun getTaskInfoTea(
        @Field("token") token: String? = null,
        @Field("id") id: String? = null,
        @Field("type") type: String? = null
    ): BaseResponse<Any>

    /**
     *学生更新接收任务状态
     */
    @POST("/api/v17/student/tasks/modify")
    suspend fun modifyTaskInfoStu(
        @Body requestBody: TaskLogRequest
    ): BaseResponse<Any>

    /**
     *教师更新接收任务状态
     */
    @POST("/api/v17/teacher/tasks/modify")
    suspend fun modifyTaskInfoTea(
        @Body requestBody: TaskLogRequest
    ): BaseResponse<Any>

    /**
     *教师发布任务驳回
     */
    @POST("/api/v17/teacher/tasks/examine")
    suspend fun refuseTask(
        @Body requestBody: TaskLogRequest
    ): BaseResponse<Any>

    /**
     *教师发布任务驳回
     */
    @POST("/api/v17/teacher/tasks/modifyTask")
    suspend fun modifyTaskStatus(
        @Body requestBody: TaskBean
    ): BaseResponse<Any>

    /**
     * 学生更换手机号短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/mSmsCode")
    suspend fun onSmsCodeChangeStu(
        @Field("token") token: String? = null,
        @Field("phone") phone: String?
    ): BaseResponse<CaptchaResponse>

    /**
     *  教师更换手机号短信验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/user/teacher/mSmsCode")
    suspend fun onSmsCodeChangeTea(
        @Field("token") token: String? = null,
        @Field("phone") phone: String?
    ): BaseResponse<Any>

    /**
     * 学生更换手机号短信验证码验证
     */
    @FormUrlEncoded
    @POST("/api/v17/user/student/checkCode")
    suspend fun changePhoneStu(
        @Field("token") token: String? = null,
        @Field("phone") phone: String? = null,
        @Field("vcode") vcode: String?
    ): BaseResponse<CaptchaResponse>

    /**
     *  教师更换手机号短信验证码验证
     */
    @FormUrlEncoded
    @POST("/api/v17/user/teachers/checkCode")
    suspend fun changePhoneTea(
        @Field("token") token: String? = null,
        @Field("phone") phone: String? = null,
        @Field("vcode") vcode: String?
    ): BaseResponse<CaptchaResponse>

    /**
     *  教师删除草稿任务
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/tasks/delTask")
    suspend fun delTaskDraft(
        @Field("token") token: String? = null,
        @Field("id") id: String?
    ): BaseResponse<CaptchaResponse>

    /**
     *工资条明细
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/wages/detail")
    suspend fun getSalaryDetail(
        @Field("token") token: String?,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *员工工资条已读状态
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/wages/modifyWD")
    suspend fun readSalaryDetail(
        @Field("token") token: String?,
        @Field("id") id: String?,
        @Field("read") read: String?="1"
    ): BaseResponse<Any>

    /**
     *工资列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/wages/mywdlists")
    suspend fun getSalaryList(
        @Field("token") token: String?,
        @Field("type") type: String?,
        @Field("lastid") lastid: String?
    ): BaseResponse<Any>

    /**
     *员工工资条验证码
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/wages/smsCode")
    suspend fun getSalaryCaptcha(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *员工工资条临时token
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/wages/tmpToken")
    suspend fun getTmpToken(
        @Field("token") token: String?,
        @Field("code") code: String?
    ): BaseResponse<Any>

    /**
     *申请设备/教室维修
     */
    @POST("/api/v17/admin/repair/myadd")
    suspend fun addRepair(
        @Body requestBody: RepairBody
    ): BaseResponse<Any>

    /**
     *添加场地预约
     */
    @POST("/api/v17/admin/spacebook/add")
    suspend fun addBookSite(
        @Body requestBody: AddBookSiteBody
    ): BaseResponse<Any>

    /**
     *修改场地预约
     */
    @POST("/api/v17/admin/spacebook/modify")
    suspend fun modifyBookSite(
        @Body requestBody: AddBookSiteBody
    ): BaseResponse<Any>

    /**
     *报修/维修记录列表按id拉列表
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/repair/listsByID")
    suspend fun getPropertyRecord(
        @Field("token") token: String?,
        @Field("lastid") lastid: String?,
        @Field("status") status: String?,
        @Field("type") type: String?
    ): BaseResponse<Any>

    /**
     *查看报修类型列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/repair/listsType")
    suspend fun getPropertyType(
        @Field("token") token: String?,
        @Field("typeid") typeid: String?,
        @Field("lastid") lastid: String?
    ): BaseResponse<Any>

    /**
     *查看报修设备列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/repair/listsDevice")
    suspend fun getDeviceType(
        @Field("token") token: String?,
        @Field("typeid") typeid: String?
    ): BaseResponse<Any>
    /**
     *我的预约列表瀑布流
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/spacebook/myListsByLID")
    suspend fun getBookSiteRecord(
        @Field("token") token: String?,
        @Field("lastid") lastid: String?
    ): BaseResponse<Any>

    /**
     *可预约场地列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/spacebook/listRooms")
    suspend fun getCanBookRooms(
        @Field("token") token: String?,
        @Field("booktime") booktime: String?
    ): BaseResponse<Any>

    /**
     *可预约场地列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/spacebook/booklists")
    suspend fun getBookList(
        @Field("token") token: String?,
        @Field("starttime") start: String?,
        @Field("endtime") end: String?
    ): BaseResponse<Any>

    /**
     *任务提醒接口
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/tasks/remind")
    suspend fun remindUnread(
        @Field("token") token: String?,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *可预约场地列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/spacebook/listRooms17")
    suspend fun getBookRoomList(
        @Field("token") token: String?,
        @Field("starttime") start: String?,
        @Field("endtime") end: String?
    ): BaseResponse<Any>

    /**
     *可预约场地列表
     */
    @FormUrlEncoded
    @POST("/api/v17/admin/spacebook/myBookLists")
    suspend fun getBookListMonth(
        @Field("token") token: String?,
        @Field("month") month: String?,
        @Field("starttime") start: String?=null,
        @Field("endtime") end: String?=null
    ): BaseResponse<Any>

    /**
     *设备token上报
     */
    @POST("/api/v17/global/setting/upToken")
    suspend fun upToken(
        @Body requestBody: UpTokenBean
    ): BaseResponse<Any>

    /**
     *学期
     */
    @FormUrlEncoded
    @POST("/api/v17/global/setting/semesters")
    suspend fun getSemester(
        @Field("token") token: String?,
    ): BaseResponse<Any>

    /**
     *更新报修记录
     */
    @POST("/api/v17/teacher/repair/modify")
    suspend fun modifyRepair(
        @Body requestBody: RepairBody
    ): BaseResponse<Any>

    /**
     *提醒维修/报修记录
     */
    @FormUrlEncoded
    @POST("/api/v17/teacher/repair/remind")
    suspend fun remindRepair(
        @Field("token") token: String?,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *私有云我的文件夹
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folder/mylists")
    suspend fun getPriCloudList(
        @Field("token") token: String?,
        @Field("uid") uid: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *老师列表德育评比评分
     */
    @FormUrlEncoded
    @POST("/api/v17/moral/moralType/lists")
    suspend fun getMoralTypeList(
        @Field("token") token: String?
    ): BaseResponse<Any>

    /**
     *查询德育评比类型信息
     */
    @FormUrlEncoded
    @POST("/api/v17/moral/moralType/info")
    suspend fun getMoralTypeInfo(
        @Field("token") token: String?,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *私有云文件夹文件
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folder/myFiles")
    suspend fun getPriCloudFiles(
        @Field("token") token: String?,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *授权文件夹列表
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folderrs/lists")
    suspend fun getPubCloudList(
        @Field("token") token: String?,
        @Field("uid") uid: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *共享文件夹的文件

     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folder/myFolderFiles")
    suspend fun getPubCloudFiles(
        @Field("token") token: String?,
        @Field("uid") uid: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *文件夹授权用户
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folderrs/authUsers")
    suspend fun getAuthUsers(
        @Field("token") token: String?,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *复制文件到文件夹
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/files/copyFile")
    suspend fun copyCloudFile(
        @Field("token") token: String?,
        @Field("id") id: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *移动文件到文件夹
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/files/moveFile")
    suspend fun moveCloudFile(
        @Field("token") token: String?,
        @Field("id") id: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *删除我的文件
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/files/del")
    suspend fun delMyCloudFile(
        @Field("token") token: String?,
        @Field("fileid") fileid: String?
    ): BaseResponse<Any>

    /**
     *删除文件夹
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folder/del")
    suspend fun delCloudFolder(
        @Field("token") token: String?,
        @Field("id") id: String?
    ): BaseResponse<Any>

    /**
     *文件夹取消授权
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folderrs/del")
    suspend fun cancelFolderAuth(
        @Field("token") token: String?,
        @Field("id") id: String?=null,
        @Field("folderid") folderid: String?
    ): BaseResponse<Any>

    /**
     *上传我的文件
     */
    @POST("/api/v17/disk/files/add")
    suspend fun addFile(
        @Body fileBean:DiskFileBean
    ): BaseResponse<Any>

    /**
     *修改我的文件
     */
    @POST("/api/v17/disk/files/modify")
    suspend fun modifyFile(
        @Body fileBean:FolderModifyBean
    ): BaseResponse<Any>

    /**
     *修改文件夹
     */
    @POST("/api/v17/disk/folder/modify")
    suspend fun modifyFolder(
        @Body fileBean:FolderModifyBean
    ): BaseResponse<Any>


    /**
     *创建文件夹
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folder/add")
    suspend fun newFileFolder(
        @Field("token") token: String?,
        @Field("parentid") parentid: String?,
        @Field("foldername") foldername: String?,
        @Field("type") type: String?=null,
        @Field("status") status: String?=null
    ): BaseResponse<Any>

    /**
     *文件夹授权
     */
    @FormUrlEncoded
    @POST("/api/v17/disk/folderrs/add")
    suspend fun setFileFolder(
        @Field("token") token: String?,
        @Field("parentid") parentid: String?=null,
        @Field("folderid") folderid: String?,
        @Field("uid") uid: String?=null,
        @Field("usertype") usertype: String?=null,
        @Field("sendlabel") sendlabel: String?=null,
        @Field("involve") involve: String?=null,
        @Field("status") status: String?=null
    ): BaseResponse<Any>



}

