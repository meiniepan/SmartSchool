package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_circular.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class CircularFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()
    var mData = ArrayList<NoticeBean>()
    override fun getLayoutId(): Int = R.layout.fragment_circular

    companion object {
        fun getInstance() :Fragment {
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
        mViewModel.getNoticeList()
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
        tvCircular.setChecked(true)
        tvSchedule.setChecked(false)
        vpCircular.setCurrentItem(0, true)
        setStatusBarDark()
    }

    private fun checkSecondTab() {
        tvCircular.setChecked(false)
        tvSchedule.setChecked(true)
        vpCircular.setCurrentItem(1, true)
        setStatusBarDark()
    }

    private fun initViewPager() {
        fragmentList.add(NoticeFragment.getInstance())
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
            response?.let {
                showSuccess()
                mData.clear()
                for (i in it.data) {
                    if (i.type == "system")
                        mData.add(i)
                }
            }
        })
    }


}