package com.xiaoneng.ss.base.view

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.Bugly
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.ErrorCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.oppo.OppoRegister
import org.android.agoo.vivo.VivoRegister
import org.android.agoo.xiaomi.MiPushRegistar


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 14:27
 */
open class BaseApplication : Application() {
    private val TAG: String? = "====="
    val context: Context by lazy {
        instance.applicationContext
    }

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SPreference.setContext(applicationContext)
        initPush()
        Bugly.init(getApplicationContext(), "c55b4f8e6e", false)
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
        initSmartRefreshHeaderFooter()
    }

    private fun initPush() {

        PushAgent.getInstance(this).onAppStart()

        UMConfigure.init(
            this,
            "5f8fe60b2065791705f41ce5",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            "ab59d5ba71c396e8c388d2d9b309eb28"
        )
//获取消息推送代理示例
//获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(this)
//注册推送服务，每次调用register方法都会回调该接口
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                Log.e(TAG, "注册失败：-------->  s:$s,s1:$s1")
            }
        })
        /**
         * 初始化厂商通道
         */
        HuaWeiRegister.register(this)
        MiPushRegistar.register(this, "2882303761518747426", "5581874725426")
        //vivo 通道
        VivoRegister.register(this)
        //OPPO通道，参数1为app key，参数2为app secret
        OppoRegister.register(this, "5cbd913970a44ecc9e54e536f68d1fe8", "0d004009e32f42149a6c5e6c8fbf40cd")

    }


    private fun initSmartRefreshHeaderFooter() {
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