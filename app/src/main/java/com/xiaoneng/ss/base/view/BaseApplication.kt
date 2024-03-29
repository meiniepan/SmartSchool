package com.xiaoneng.ss.base.view

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.entity.UMessage
import com.xiaoneng.ss.BuildConfig
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.ErrorCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import com.xiaoneng.ss.common.utils.eventBus.OnPushEvent
import com.xiaoneng.ss.model.MyPushBean
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.oppo.OppoRegister
import org.android.agoo.vivo.VivoRegister
import org.android.agoo.xiaomi.MiPushRegistar
import skin.support.SkinCompatManager
import skin.support.app.SkinAppCompatViewInflater
import skin.support.app.SkinCardViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.design.app.SkinMaterialViewInflater


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
        if (!BuildConfig.IS_DEBUG) {
        Bugly.init(getApplicationContext(), "c55b4f8e6e", false)
            Beta.autoCheckUpgrade = true
        }
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
        initSmartRefreshHeaderFooter()
        resetDownLoad()
//        initSkin()
    }

//    private fun initSkin() {
//        SkinCompatManager.withoutActivity(this)
//            .addInflater(SkinAppCompatViewInflater()) // 基础控件换肤初始化
//            .addInflater(SkinMaterialViewInflater()) // material design 控件换肤初始化[可选]
//            .addInflater(SkinConstraintViewInflater()) // ConstraintLayout 控件换肤初始化[可选]
//            .addInflater(SkinCardViewInflater()) // CardView v7 控件换肤初始化[可选]
////            .setSkinStatusBarColorEnable(false) // 关闭状态栏换肤，默认打开[可选]
////            .setSkinWindowBackgroundEnable(false) // 关闭windowBackground换肤，默认打开[可选]
//            .loadSkin()
//
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration?) {
//        super.onConfigurationChanged(newConfig)
//        //适配 Dark Mode
//        val nightModeFlags:Int = resources.configuration.uiMode and
//                Configuration.UI_MODE_NIGHT_MASK
//        Log.e("theme====", nightModeFlags.toString() )
//        when (nightModeFlags) {
//            Configuration.UI_MODE_NIGHT_YES -> {
//                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
//
//            }
//            Configuration.UI_MODE_NIGHT_NO -> {
//                SkinCompatManager.getInstance().restoreDefaultTheme(); // 后缀加载
//            }
//            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
//            }
//        }
//    }

    private fun resetDownLoad() {
        FileTransInfo.reset()
        FileDownloadInfo.reset()
    }

    private fun initPush() {
        try {
            UMConfigure.init(
                this,
                "5f8fe60b2065791705f41ce5",
                "Umeng",
                UMConfigure.DEVICE_TYPE_PHONE,
                "ab59d5ba71c396e8c388d2d9b309eb28"
            )
            //获取消息推送代理示例
            val mPushAgent = PushAgent.getInstance(this)
            mPushAgent.setMessageHandler(object : UmengMessageHandler() {
                override fun dealWithNotificationMessage(p0: Context?, p1: UMessage?) {
                    Log.w("=====", "mPushAgent")
                    OnPushEvent(MyPushBean()).post()
                    super.dealWithNotificationMessage(p0, p1)
                }

            })
            //注册推送服务，每次调用register方法都会回调该接口
            mPushAgent.register(object : IUmengRegisterCallback {
                override fun onSuccess(deviceToken: String) {
                    //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                    var mDeviceToken: String by SPreference(Constant.DEVICE_TOKEN, "")
                    mDeviceToken = deviceToken
                    Log.i(TAG, "注册成功：deviceToken：-------->  $deviceToken")
                }

                override fun onFailure(s: String, s1: String) {
                    Log.e(TAG, "注册失败：-------->  s:$s,s1:$s1")
                }
            })


            //该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
            mPushAgent.onAppStart()

            /**
             * 初始化厂商通道
             */
            HuaWeiRegister.register(this)
            MiPushRegistar.register(this, "2882303761518747426", "5581874725426")
            //vivo 通道
            VivoRegister.register(this)
            //OPPO通道，参数1为app key，参数2为app secret
            OppoRegister.register(
                this,
                "5cbd913970a44ecc9e54e536f68d1fe8",
                "0d004009e32f42149a6c5e6c8fbf40cd"
            )
        } catch (e: ClassNotFoundException) {
        } catch (e: Exception) {
        }
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