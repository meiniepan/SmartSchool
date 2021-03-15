package com.xiaoneng.ss.module.circular.view

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.toIntSafe
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.interfaces.INoticeUnread
import kotlinx.android.synthetic.main.activity_notice.*
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
        flSysMsg.setOnClickListener {
            mStartActivity<SystemMsgActivity>(context) {
                putExtra(Constant.DATA, mData)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getNoticeList(type = "system")
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
        fragmentList.add(NoticeFragment.getInstance(this))
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
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let { hData ->
                showSuccess()
                mData.clear()
                hData.data?.let {

                    mData.addAll(it)
                    if (hData.unread == "1") {
                        vBadge.visibility = View.VISIBLE
                    } else {
                        vBadge.visibility = View.GONE
                    }
                }
            }
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshNotice(event: RefreshUnreadEvent) {
        var unread = event.unread
        if (unread == "0" || TextUtils.isEmpty(unread)) {
            unread = "通知"
        } else if (unread.toIntSafe()>99) {
            unread = "通知(99+)"
        } else {
            unread = "通知($unread)"
        }
        tvCircular.setText(unread)
    }
}