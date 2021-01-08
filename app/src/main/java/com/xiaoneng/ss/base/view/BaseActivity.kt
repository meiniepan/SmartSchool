package com.xiaoneng.ss.base.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.AppManager
import com.xiaoneng.ss.common.utils.ColorUtil
import com.xiaoneng.ss.common.utils.RevealUtil.circularFinishReveal
import com.xiaoneng.ss.common.utils.RevealUtil.setReveal
import com.xiaoneng.ss.common.utils.eventBus.ChangeThemeEvent
import com.zackratos.ultimatebarx.library.UltimateBarX
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 19:56
 */
abstract class BaseActivity : AppCompatActivity() {


    lateinit var mRootView: View

    val loadService: LoadService<*> by lazy {
        val contentLayout = findViewById<View>(R.id.contentLayout)
        LoadSir.getDefault().register(contentLayout) {
            reLoad()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mRootView = (findViewById(android.R.id.content) as ViewGroup).getChildAt(0)
        AppManager.addActivity(this)
        initView()
        initData()
        if (showCreateReveal()) {
            setUpReveal(savedInstanceState)
        }
        EventBus.getDefault().register(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
//        if (Date().month + 1 == 4 && Date().date == 4) {
//            initStatusColor(getColor(R.color.colorGray666))
//            if ("FrameLayout" == name) {
//                val count: Int = attrs.getAttributeCount()
//                for (i in 0 until count) {
//                    val attributeName: String = attrs.getAttributeName(i)
//                    val attributeValue: String = attrs.getAttributeValue(i)
//                    if (attributeName == "id") {
//                        val id = attributeValue.substring(1).toInt()
//                        val idVal = resources.getResourceName(id)
//                        if ("android:id/content" == idVal) {
//                            return GrayFrameLayout(context, attrs)
//                        }
//                    }
//                }
//            }
//        } else {
//        }
        return super.onCreateView(name, context, attrs)
    }


    open fun showCreateReveal(): Boolean = true

    open fun showDestroyReveal(): Boolean = false

    open fun initView() {}
    open fun initData() {

    }

    abstract fun getLayoutId(): Int

    open fun reLoad() {
        getData()
    }

    open fun getData() {

    }


    fun setUpReveal(savedInstanceState: Bundle?) {
        setReveal(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        AppManager.removeActivity(this)
    }

    override fun onPause() {
        super.onPause()
        if (showDestroyReveal()) {
            circularFinishReveal(mRootView)
        }
    }

    override fun finish() {
        super.finish()
        if (showDestroyReveal()) {
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
        } else {
            overridePendingTransition(
                R.anim.animo_alph_open,
                R.anim.animo_alph_close
            )
        }
    }

    open fun initStatusBar() {
        UltimateBarX.with(this)
            .fitWindow(false)
            .color(Color.TRANSPARENT)
//            .drawableRes(R.drawable.bac_blue_bac_19)
            .light(true)
            .applyStatusBar()
    }

    @SuppressLint("NewApi")
    fun initStatusColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = if (color == 0) ColorUtil.getColor(this) else color
        }
        if (ColorUtils.calculateLuminance(color) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // 跟随系统
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeThemeEvent(event: ChangeThemeEvent) {
        initStatusColor(0)
    }

}