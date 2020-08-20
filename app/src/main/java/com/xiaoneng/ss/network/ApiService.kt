package com.xiaoneng.ss.network

import com.xiaoneng.ss.account.model.*
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
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
        @Field("id") page: String = "",
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
        @Field("id") page: String = "",
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
        @Field("id") page: String = ""
    ): BaseResponse<NoticeDetailBean>

    /**
     *教师通知详情
     */
    @FormUrlEncoded
    @POST("/api/v1/teacher/notices/info")
    suspend fun getNoticeDetail2(
        @Field("token") token: String,
        @Field("id") page: String = ""
    ): BaseResponse<NoticeDetailBean>
}