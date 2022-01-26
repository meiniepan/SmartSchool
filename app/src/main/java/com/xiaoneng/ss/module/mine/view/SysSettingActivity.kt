package com.xiaoneng.ss.module.mine.view

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.tencent.bugly.beta.Beta
import com.xiaoneng.ss.BuildConfig
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import kotlinx.android.synthetic.main.activity_sys_setting.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class SysSettingActivity : BaseLifeCycleActivity<AccountViewModel>() {
    private var devUrl: String by SPreference(Constant.DEV_URL, Constant.BASE_URL_DEV)

    override fun getLayoutId(): Int = R.layout.activity_sys_setting

    override fun initView() {
        super.initView()
        tvSettingItem3.text = getCacheSize(this).formatMemorySize()
        var vType = ""
        if (BuildConfig.IS_DEBUG) {
            if (devUrl == Constant.BASE_URL) {
                vType = "_ONLINE"
                llDevSetting.setOnClickListener {
                    logout()
                    devUrl = Constant.BASE_URL_DEV
                    toast("切换成功，重新启动后生效！")
                }
            } else {
                vType = "_DEV"
                llDevSetting.setOnClickListener {
                    logout()
                    devUrl = Constant.BASE_URL
                    toast("切换成功，重新启动后生效！")
                }
            }
            llDevSetting.visibility = View.VISIBLE
        } else {
            llDevSetting.visibility = View.GONE
        }
        tvSettingItem1.text = "当前版本(" + BuildConfig.VERSION_NAME + vType + ")"
        llItem1Setting.setOnClickListener {
            Beta.checkUpgrade(true, false)
        }

        llItem2Setting.setOnClickListener {
            mStartActivity<UserProtocolActivity>(this)
        }

        llItem3Setting.setOnClickListener {
            clearCache(this)
        }
        llItemZhuxiao.setOnClickListener {
            mAlert(
                "变更/删除个人信息及注销账号请联系您所在学校的相关工作人员，对方将在15个工作日内完成核查和处理。",
                "温馨提示"
            ) {}
        }
        llItem4Setting.setOnClickListener {
            mAlert(
                "退出登录后将无法接收该账号信息",
                "昰否确认退出该账号"
            ) {
                logout()
            }
        }
    }

    override fun initData() {
        super.initData()
    }

    private fun logout() {
        mViewModel.logout()
        showLoading()
    }

    private fun getCacheSize(context: Context): Long {
        val cacheSize = this.cacheDir.size()
        val fileSize = context.filesDir.size()
        return cacheSize + fileSize
    }

    fun clearCache(context: Context) {
        val cacheSize = getCacheSize(context)
        if (cacheSize > 0) {
            context.mAlert("清除所有缓存？") {
                context.cacheDir.suicide()
                context.filesDir.suicide()
                mToast("成功清除${cacheSize.formatMemorySize()}缓存")
                tvSettingItem3.text = "0K"
            }
        } else mToast("无需清理")
    }

    override fun initDataObserver() {
        mViewModel.mLogoutData.observe(this, Observer { response ->
            response?.let {
                mainLogin(this)
            }
        })

    }

    fun File.size(): Long {
        var size = 0L
        try {
            if (isFile) {
                size += length()
            }
            if (isDirectory) {
                listFiles().forEach {
                    size += it.size()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    fun File.suicide() {
        if (isFile) delete()
        if (isDirectory) listFiles().forEach { it.suicide() }
    }

}