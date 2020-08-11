package com.xiaoneng.ss.base.repository

import com.xiaoneng.ss.network.ApiService
import com.xiaoneng.ss.network.RetrofitFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/25
 * Time: 20:40
 */
abstract class ApiRepository : BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}