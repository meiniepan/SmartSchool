package com.xiaoneng.ss.network

import com.xiaoneng.ss.network.response.BaseResponse
import com.xiaoneng.ss.network.response.EmptyResponse
import com.xiaoneng.ss.module.account.model.LoginResponse
import com.xiaoneng.ss.module.account.model.RegisterResponse

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/25
 * Time: 20:41
 */

interface ApiService {

    @POST("/user/login")
    fun onLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Observable<BaseResponse<LoginResponse>>

//    @POST("/user/register")
//    fun onRegister(
//        @Query("username") username: String, @Query("password") password: String,
//        @Query("repassword") repassword: String
//    ): Observable<BaseResponse<RegisterResponse>>
//
//    @POST("/lg/collect/{id}/json")
//    fun collect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("/lg/uncollect_originId/{id}/json")
//    fun unCollect(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>
//
//    @GET("/banner/json")
//    fun loadBanner(): Observable<BaseResponse<List<BannerResponse>>>
//
//    @GET("/article/top/json")
//    fun loadTopArticle(): Observable<BaseResponse<List<Article>>>
//
//    @GET("/article/list/{pageNum}/json")
//    fun loadHomeArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<HomeArticleResponse>>
//
//    @GET("/wxarticle/chapters/json")
//    fun loadWeChatTab(): Observable<BaseResponse<List<WeChatTabNameResponse>>>
//
//    @GET("/wxarticle/list/{cid}/{pageNum}/json")
//    fun loadWeChatArticles(@Path("cid") cid: Int, @Path("pageNum") page: Int)
//            : Observable<BaseResponse<WeChatArticleResponse>>
//
//    @GET("/tree/json")
//    fun loadSystemTab(): Observable<BaseResponse<List<SystemTabNameResponse>>>
//
//    @GET("/article/list/{pageNum}/json")
//    fun loadSystemArticles(
//        @Path("pageNum") pageNum: Int,
//        @Query("cid") id: Int?
//    ): Observable<BaseResponse<SystemArticleResponse>>
//
//    @GET("/project/tree/json")
//    fun loadProjectTab(): Observable<BaseResponse<List<ProjectTabResponse>>>
//
//    @GET("/project/list/{pageNum}/json")
//    fun loadProjectArticles(
//        @Path("pageNum") pageNum: Int,
//        @Query("cid") cid: Int
//    ): Observable<BaseResponse<ProjectResponse>>
//
//    @GET("/navi/json")
//    fun loadNavigationTab(): Observable<BaseResponse<List<NavigationTabNameResponse>>>
//
//    @GET("/lg/collect/list/{pageNum}/json")
//    fun loadCollectArticle(@Path("pageNum") page: Int): Observable<BaseResponse<CollectResponse>>
//
//    @POST("/lg/uncollect/{id}/json")
//    fun unCollect(
//        @Path("id") id: Int,
//        @Query("originId") originId: Int
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("lg/collect/add/json")
//    fun addCollectArticle(
//        @Query("title") title: String,
//        @Query("author") author: String,
//        @Query("link") link: String
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @GET("/lg/todo/v2/list/{pageNum}/json")
//    fun loadTodoData(@Path("pageNum") pageNum: Int): Observable<BaseResponse<TodoPageResponse>>
//
//    @POST("/lg/todo/add/json")
//    fun addTodo(
//        @Query("title") title: String,
//        @Query("content") content: String,
//        @Query("date") date: String,
//        @Query("type") type: Int,
//        @Query("priority") priority: Int
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("/lg/todo/delete/{id}/json")
//    fun deleteTodo(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("/lg/todo/update/{id}/json")
//    fun updateTodo(
//        @Path("id") id: Int?,
//        @Query("title") title: String,
//        @Query("content") content: String,
//        @Query("date") date: String,
//        @Query("type") type: Int,
//        @Query("priority") priority: Int
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("/lg/todo/done/{id}/json")
//    fun finishTodo(
//        @Path("id") id: Int,
//        @Query("status") status: Int
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @GET("hotkey/json")
//    fun loadHotKey(): Observable<BaseResponse<List<HotKeyResponse>>>
//
//    @POST("/article/query/{pageNum}/json")
//    fun loadSearchResult(
//        @Path("pageNum") pageNum: Int,
//        @Query("k") key: String
//    ): Observable<BaseResponse<SearchResultResponse>>
//
//    @GET("wenda/list/{pageNum}/json")
//    fun loadQuestionList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<QuestionResponse>>
//
//    @GET("user/lg/private_articles/{pageNum}/json")
//    fun loadMeShareArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<MeShareResponse<MeShareArticleResponse>>>
//
//    @GET("user_article/list/{pageNum}/json")
//    fun loadSquareArticle(@Path("pageNum") pageNum: Int): Observable<BaseResponse<SquareResponse>>
//
//    @POST("lg/user_article/delete/{id}/json")
//    fun deleteShareArticle(@Path("id") id: Int): Observable<BaseResponse<EmptyResponse>>
//
//    @POST("lg/user_article/add/json")
//    fun addShareArticle(
//        @Query("title") title: String,
//        @Query("link") link: String
//    ): Observable<BaseResponse<EmptyResponse>>
//
//    @GET("coin/rank/{pageNum}/json")
//    fun loadRankList(@Path("pageNum") pageNum: Int): Observable<BaseResponse<RankResponse>>
//
//    @GET("lg/coin/userinfo/json")
//    fun loadMeRankInfo(): Observable<BaseResponse<IntegralResponse>>
//
//    @GET("/lg/coin/list/{pageNum}/json")
//    fun loadIntegralHistory(@Path("pageNum") pageNum: Int): Observable<BaseResponse<IntegralHistoryListResponse>>
//
//    // 使用协程 + Retrofit2.6
//    @POST("/lg/collect/{id}/json")
//    suspend fun collectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>
//
//    @POST("/lg/uncollect_originId/{id}/json")
//    suspend fun unCollectCo(@Path("id") id: Int): BaseResponse<EmptyResponse>
//
//    @GET("/banner/json")
//    suspend fun loadBannerCo(): BaseResponse<List<BannerResponse>>
//
//    @GET("/article/top/json")
//    suspend fun loadTopArticleCo(): BaseResponse<List<Article>>
//
//    @GET("/article/list/{pageNum}/json")
//    suspend fun loadHomeArticleCo(@Path("pageNum") pageNum: Int): BaseResponse<HomeArticleResponse>

    @POST("/user/login")
    suspend fun onLoginCo(
        @Query("username") username: String,
        @Query("password") password: String
    ): BaseResponse<LoginResponse>

    @POST("/login/sloginin")
    suspend fun onSsLoginCo(
        @Query("phone") phone: String,
        @Query("vcode") vcode: String,
        @Query("spassword") password: String
    ): BaseResponse<LoginResponse>

    @POST("/user/register")
    suspend fun onRegisterCo(
        @Query("username") username: String, @Query("password") password: String,
        @Query("repassword") repassword: String
    ): BaseResponse<RegisterResponse>

//    @GET("/wxarticle/chapters/json")
//    suspend fun loadWeChatTabCo(): BaseResponse<List<WeChatTabNameResponse>>
//
//    @GET("/wxarticle/list/{cid}/{pageNum}/json")
//    suspend fun loadWeChatArticlesCo(@Path("cid") cid: Int, @Path("pageNum") page: Int)
//            : BaseResponse<WeChatArticleResponse>
//
//    @GET("/tree/json")
//    suspend fun loadSystemTabCo(): BaseResponse<List<SystemTabNameResponse>>
//
//    @GET("/article/list/{pageNum}/json")
//    suspend fun loadSystemArticlesCo(
//        @Path("pageNum") pageNum: Int,
//        @Query("cid") id: Int?
//    ): BaseResponse<SystemArticleResponse>
//
//    @GET("/project/tree/json")
//    suspend fun loadProjectTabCo(): BaseResponse<List<ProjectTabResponse>>
//
//    @GET("/project/list/{pageNum}/json")
//    suspend fun loadProjectArticlesCo(
//        @Path("pageNum") pageNum: Int,
//        @Query("cid") cid: Int
//    ): BaseResponse<ProjectResponse>
//
//    @GET("/navi/json")
//    suspend fun loadNavigationTabCo(): BaseResponse<List<NavigationTabNameResponse>>
//
//    @GET("/lg/collect/list/{pageNum}/json")
//    suspend fun loadCollectArticleCo(@Path("pageNum") page: Int): BaseResponse<CollectResponse>
}