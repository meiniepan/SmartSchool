package com.xiaoneng.ss.module.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.sys.view.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.Subscribe


class MainActivity : BaseActivity(){
    // 委托属性   将实现委托给了 -> Preference
    private var mUsername: String by SPreference(Constant.USERNAME_KEY, "未登录")
    private var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    private val mPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private lateinit var mToolbarTitles: List<String>

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        initToolbarTitles()
        initToolBar()
        initDrawerLayout()
        initColor()
        initBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        // 判断当前是recreate还是新启动
        if (savedInstanceState == null) {
            switchFragment(Constant.HOME)
            checkUpdate(this, false)
        }
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
        switchFragment(mLastIndex)
    }

    private fun initToolbarTitles() {
        mToolbarTitles = arrayListOf(
            getString(R.string.navigation_1),
            getString(R.string.navigation_2),
            getString(R.string.navigation_3),
            getString(R.string.navigation_4)
        )
    }

    private fun initToolBar() {
        //设置导航图标、按钮有旋转特效
        val toggle = ActionBarDrawerToggle(
            this, drawer_main, toolbar, R.string.app_name, R.string.app_name
        )
        drawer_main.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initColor() {
        toolbar.setBackgroundColor(ColorUtil.getColor(this))
        bottom_navigation.setItemIconTintList(ColorUtil.getColorStateList(this))
        bottom_navigation.setItemTextColor(ColorUtil.getColorStateList(this))
        bottom_navigation.setBackgroundColor(ContextCompat.getColor(this, R.color.white_bg))
    }

    private fun initDrawerLayout() {


    }

    private fun initFabButton() {

    }

    private fun initBottomNavigation() {
        bottom_navigation.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(Constant.HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_system -> {
                    switchFragment(Constant.SYSTEM)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wechat -> {
                    switchFragment(Constant.WECHAT)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_navigation -> {
                    switchFragment(Constant.NAVIGATION)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction =
            fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
        setToolBarTitle(toolbar, mToolbarTitles[mLastIndex])
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                Constant.HOME -> fragment = SystemFragment.getInstance()
                Constant.SYSTEM -> fragment = SystemFragment.getInstance()
                Constant.NAVIGATION -> fragment = SystemFragment.getInstance()
                Constant.WECHAT -> fragment = SystemFragment.getInstance()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }

    fun setToolBarTitle(toolbar: Toolbar, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //将滑动菜单显示出来
            android.R.id.home -> {
                drawer_main.openDrawer(Gravity.START)
                return true
            }
            R.id.action_scan -> {
//                initCameraPermission()
            }
            R.id.action_search -> {
//                startActivity<SearchActivity>(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun showCreateReveal(): Boolean = false

    @Subscribe
    fun settingEvent(event: ChangeThemeEvent) {
        initColor()
    }

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


}
