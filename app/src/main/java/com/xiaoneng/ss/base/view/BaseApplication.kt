package com.xiaoneng.ss.base.view

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.tencent.bugly.Bugly
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.ErrorCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 14:27
 */
open class BaseApplication : Application() {
    val context: Context by lazy {
        instance.applicationContext
    }
    companion object {
        lateinit var instance : BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        SPreference.setContext(applicationContext)
        initTpns()
        Bugly.init(getApplicationContext(), "c55b4f8e6e", false)
        instance = this
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
        initSmartRefreshHeaderFooter()
    }

    private fun initTpns() {
        XGPushConfig.setMiPushAppId(applicationContext, "2882303761518744928");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5751874450928");
//打开第三方推送
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.enableDebug(this,true)
        XGPushManager.registerPush(this, object : XGIOperateCallback {
            override fun onSuccess(data: Any, flag: Int) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：$data")
                XGPushManager.setTag(applicationContext,"aaaa")
            }

            override fun onFail(data: Any?, errCode: Int, msg: String) {
                Log.d("TPush", "注册失败，错误码：$errCode,错误信息：$msg")
            }
        })

    }


    private  fun initSmartRefreshHeaderFooter() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
//            RefreshViewHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            ClassicsHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
//            RefreshViewFooter(context)
            ClassicsFooter(context)
        }
    }
    private fun initMode() {
        var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}