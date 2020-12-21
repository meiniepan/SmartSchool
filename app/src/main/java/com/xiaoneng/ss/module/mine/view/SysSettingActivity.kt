package com.xiaoneng.ss.module.mine.view

import android.content.Context
import androidx.lifecycle.Observer
import com.tencent.bugly.beta.Beta
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
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

    override fun getLayoutId(): Int = R.layout.activity_sys_setting


    override fun initView() {
        super.initView()
        tvSettingItem3.text = getCacheSize(this).formatMemorySize()

        llItem1Setting.setOnClickListener {
            Beta.checkUpgrade(true,false)
        }

        llItem2Setting.setOnClickListener {
            mStartActivity<UserProtocolActivity>(this)
        }

        llItem3Setting.setOnClickListener {
            clearCache(this)
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
                toast("成功清除${cacheSize.formatMemorySize()}缓存")
                tvSettingItem3.text = "0K"
            }
        } else toast("无需清理")
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