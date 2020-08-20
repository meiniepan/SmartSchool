package com.xiaoneng.ss.base.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.ChangeThemeEvent
import com.xiaoneng.ss.common.utils.ColorUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/25
 * Time: 19:35
 */

abstract class BaseFragment : Fragment() {

    protected lateinit var loadService: LoadService<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), container, false)
        loadService = LoadSir.getDefault().register(rootView) { reLoad() }
        EventBus.getDefault().register(this)
        return loadService.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Date().month + 1 == 4 && Date().date == 4) {
            initStatusColor(getColor(requireContext(), R.color.colorGray666))
        } else {
            initStatusColor(0)
        }
//        setStatusBarDark()
//        initStatusColor(getColor(requireContext(), R.color.titleBlack))
        initView()
        initData()
    }

    abstract fun initView()

    open fun initData() {}

    // 重新加载
    open fun reLoad() = initData()


    abstract fun getLayoutId(): Int

    @SuppressLint("NewApi")
    private fun initStatusColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            requireActivity().window.statusBarColor =
                if (color == 0) ColorUtil.getColor(requireContext()) else color
        }
        if (ColorUtils.calculateLuminance(Color.TRANSPARENT) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // 跟随系统
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun changeThemeEvent(event: ChangeThemeEvent) {
        initStatusColor(0)
    }

    fun setStatusBarDark() {
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}