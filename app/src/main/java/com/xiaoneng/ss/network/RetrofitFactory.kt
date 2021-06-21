package com.xiaoneng.ss.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.xiaoneng.ss.BuildConfig
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/24
 * Time: 16:56
 */

class RetrofitFactory private constructor() {
    private var devUrl: String by SPreference(Constant.DEV_URL, Constant.BASE_URL_DEV)
    private val retrofit: Retrofit

    fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    init {
        var url:String
        if (BuildConfig.IS_DEBUG) {
            url = devUrl
        } else {
            url = Constant.BASE_URL
//            url = Constant.BASE_URL_DEV
        }
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }

    private fun initOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
//            .addInterceptor(initCookieIntercept())
//            .addInterceptor(initLoginIntercept())
            .addInterceptor(initCommonInterceptor())
        if (BuildConfig.IS_DEBUG) {
            builder.addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Platform.WARN)
                    .build()
            )
        }
        return builder.build()
    }

    private fun initCookieIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val requestUrl = request.url.toString()
            val domain = request.url.host
            //只保存登录或者注册
            if (requestUrl.contains(Constant.SAVE_USER_LOGIN_KEY) || requestUrl.contains(Constant.SAVE_USER_REGISTER_KEY)) {
                val mCookie = response.headers(Constant.SET_COOKIE_KEY)
                mCookie?.let {
                    saveCookie(domain, parseCookie(it))
                }
            }
            response
        }
    }

    //自动登录
    private fun initLoginIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val domain = request.url.host

            if (domain.isNotEmpty()) {
                val mCookie by SPreference("cookie", "")
                if (mCookie.isNotEmpty()) {
                    builder.addHeader(Constant.COOKIE_NAME, mCookie)
                }
            }
            val response = chain.proceed(builder.build())
            response
        }
    }

    private fun initCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
                .build()
            chain.proceed(request)
        }
    }

    private fun parseCookie(it: List<String>): String {
        if (it.isEmpty()) {
            return ""
        }

        val stringBuilder = StringBuilder()

        it.forEach { cookie ->
            stringBuilder.append(cookie).append(";")
        }

        if (stringBuilder.isEmpty()) {
            return ""
        }
        //末尾的";"去掉
        return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
    }

    private fun saveCookie(domain: String?, parseCookie: String) {
        domain?.let {
            var resutl: String by SPreference("cookie", parseCookie)
            resutl = parseCookie
        }
    }
}