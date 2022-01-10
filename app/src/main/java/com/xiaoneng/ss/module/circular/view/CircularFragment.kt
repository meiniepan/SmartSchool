package com.xiaoneng.ss.module.circular.view

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.common.utils.eventBus.CleanAllEvent
import com.xiaoneng.ss.common.utils.eventBus.OnPushEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.toIntSafe
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_circular.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class CircularFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()
    var fragment1: NoticeFragment2 = NoticeFragment2.getInstance()
    var mData = ArrayList<NoticeBean>()
    override fun getLayoutId(): Int = R.layout.fragment_circular

    companion object {
        fun getInstance(): Fragment {
            return CircularFragment()
        }

    }

    override fun initView() {
        super.initView()
        initViewPager()
        initTab()
//        flSysMsg.setOnClickListener {
//            mStartActivity<SystemMsgActivity>(context) {
//                putExtra(Constant.DATA, mData)
//            }
//        }
        tvCleanMsg.setOnClickListener {
            showLoading()
            mViewModel.readAll()
        }
    }

    override fun onResume() {
        super.onResume()
//        getData()
    }

    override fun getData() {

    }

    private fun initTab() {
        tvCircular.setOnClickListener {
            checkFirsTab()
        }
        tvSchedule.setOnClickListener {
            checkSecondTab()
        }
    }

    private fun checkFirsTab() {
        flSysMsg.visibility = View.VISIBLE
        tvCircular.setChecked(true)
        tvSchedule.setChecked(false)
        vpCircular.setCurrentItem(0, true)
    }

    private fun checkSecondTab() {
        flSysMsg.visibility = View.GONE
        tvCircular.setChecked(false)
        tvSchedule.setChecked(true)
        vpCircular.setCurrentItem(1, true)
    }

    private fun initViewPager() {
        fragmentList.add(fragment1)
        fragmentList.add(ScheduleFragment.getInstance())
        fragmentAdapter = FragmentVpAdapter(
            childFragmentManager,
            fragmentList
        )
        vpCircular.adapter = fragmentAdapter
        vpCircular.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    checkFirsTab()
                } else if (position == 1) {
                    checkSecondTab()
                }
            }
        })
    }

    override fun initDataObserver() {
        mViewModel.mReadData.observe(this, Observer { response ->
            response?.let {
                fragment1.doRefresh()
            }
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshNotice(event: RefreshUnreadEvent) {
        var unread = event.unread
        if (unread == "0" || TextUtils.isEmpty(unread)) {
            unread = "通知"
        } else if (unread.toIntSafe() > 99) {
            unread = "通知(99+)"
        } else {
            unread = "通知($unread)"
        }
        tvCircular.setText(unread)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cleanAll(event: CleanAllEvent) {
        var unread = event.unread
        if (unread == "1") {
            tvCleanMsg.visibility = View.VISIBLE
        } else {
            tvCleanMsg.visibility = View.GONE
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshPush(event: OnPushEvent) {
        getData()
    }
}