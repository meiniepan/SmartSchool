package com.xiaoneng.ss.module.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.permission.PermissionResult
import com.xiaoneng.ss.common.permission.Permissions
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.view.CircularFragment
import com.xiaoneng.ss.module.mine.view.MineFragment
import com.xiaoneng.ss.module.school.view.SchoolFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.AppSettingsDialog


class MainActivity : BaseLifeCycleActivity<AccountViewModel>() {
    private var mExitTime: Long = 0

    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by SPreference(Constant.USERNAME_KEY, "未登录")
    private var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
    private var mLastIndex: Int = -1

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()

    private val mPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initColor()
        initViewPager()
        initBottomNavigation()
    }

    override fun initData() {
        super.initData()
        //        mViewModel.getAuthority()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            contentLayout.currentItem = Constant.HOME
            checkUpdate(this, false)
        }
        initCameraPermission()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // recreate时保存当前页面位置
        outState.putInt("index", mLastIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复recreate前的页面
        mLastIndex = savedInstanceState.getInt("index")
        contentLayout.currentItem = mLastIndex
    }


    private fun initColor() {
        bottom_navigation.itemIconTintList = ColorUtil.getColorStateList(this)
        bottom_navigation.itemTextColor = ColorUtil.getColorStateList(this)
        bottom_navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun initViewPager() {
        fragmentList.add(CircularFragment.getInstance())
        fragmentList.add(SchoolFragment.getInstance())
        fragmentList.add(MineFragment.getInstance())

        fragmentAdapter = FragmentVpAdapter(
            supportFragmentManager,
            fragmentList
        )
        contentLayout.adapter = fragmentAdapter
        contentLayout.offscreenPageLimit = 1
    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    contentLayout.currentItem = Constant.HOME
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_school -> {
                    contentLayout.currentItem = Constant.SCHOOL
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_mine -> {
                    contentLayout.currentItem = Constant.MINE
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showCreateReveal(): Boolean = false


    // 获取扫描二维码的返回结果，使用浏览器打开（使用ArticleDetailActivity扫描复杂二维码会crash）
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                var intent = Intent()
                intent.action = "android.intent.action.VIEW"
                intent.data = Uri.parse(data.getStringExtra(Constant.CODED_CONTENT).toString())
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis()

        if (time - mExitTime > 2000) {
            toast(getString(R.string.exit_app))
            mExitTime = time
        } else {
            AppManager.exitApp(this)
        }
    }

    private fun initCameraPermission() {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {
//                        val intent = Intent(this@MainActivity, CaptureActivity::class.java)
//                        var config = ZxingConfig()
//                        config.isShowAlbum = false
//                        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config)
//                        startActivityForResult(intent, Constant.REQUEST_CODE_SCAN)
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                }
            }
        )
    }

    override fun initDataObserver() {

    }

}
