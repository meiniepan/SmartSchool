package com.xiaoneng.ss.module.mine.view

import android.content.Context
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mainLogin
import kotlinx.android.synthetic.main.activity_sys_setting.*
import org.jetbrains.anko.toast
import java.io.File
import java.math.BigDecimal

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SysSettingActivity : BaseLifeCycleActivity<AccountViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_sys_setting


    override fun initView() {
        super.initView()
        tvSettingItem3.text = getCacheSize(this).formatMemorySize()

        llItem1Setting.setOnClickListener {
            toast( "当前已是最新版本")
        }

        llItem1Setting.setOnClickListener {
            toast( R.string.not_open)
        }

        llItem3Setting.setOnClickListener {
            clearCache(this)
        }
        llItem4Setting.setOnClickListener {
            logout()
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

    fun Long.formatMemorySize(): String {
        val kiloByte = this / 1024.toDouble()

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            return kiloByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "K"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "M"
        }

        val teraBytes = megaByte / 1024
        if (teraBytes < 1) {
            return megaByte.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "G"
        }

        return teraBytes.toBigDecimal().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "T"
    }
}