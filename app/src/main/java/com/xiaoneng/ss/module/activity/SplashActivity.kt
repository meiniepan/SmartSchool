package com.xiaoneng.ss.module.activity

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.permission.PermissionResult
import com.xiaoneng.ss.common.permission.Permissions
import com.xiaoneng.ss.common.utils.startActivity
import com.xiaoneng.ss.module.account.view.LoginActivity
import pub.devrel.easypermissions.AppSettingsDialog

/**
 * @author Burning
 * @description:
 * @date :2020/8/10 3:02 PM
 */
class SplashActivity : AppCompatActivity() {

    private var mSplashContainer: ViewGroup? = null

    private var mContext: Context? = null

    private val mPermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = getApplicationContext()
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        mSplashContainer = findViewById<ViewGroup>(R.id.splash_container)
        initView()
    }

    /**
     * 初始化进场动画
     */
    private fun initView() {
        initPermission()
    }


    private fun startIntent() {
        startActivity<LoginActivity>(this)
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun initPermission() {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {
                        startIntent()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                        finish()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                        finish()
                    }
                }
            }
        )
    }
}