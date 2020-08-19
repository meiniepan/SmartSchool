package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.module.circular.adapter.FragmentCircularAdapter
import kotlinx.android.synthetic.main.fragment_circular.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class CircularFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    lateinit var fragmentAdapter: FragmentCircularAdapter
    var fragmentlist = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.fragment_circular

    companion object {
        fun getInstance(): CircularFragment? {
            return CircularFragment()
        }

    }

    override fun initView() {
        super.initView()
        initViewPager()
        initTab()
    }

    private fun initTab() {
        tvCircular.setOnClickListener {
            checkFirsTab()
        }
        tvSchedule.setOnClickListener {
            checkSecondTab()
        }
    }

    private fun checkSecondTab() {
        tvCircular.setChecked(false)
        tvSchedule.setChecked(true)
        vpCircular.setCurrentItem(1, true)
    }

    private fun checkFirsTab() {
        tvCircular.setChecked(true)
        tvSchedule.setChecked(false)
        vpCircular.setCurrentItem(0, true)
    }

    private fun initViewPager() {
        fragmentlist.add(NoticeFragment.getInstance())
        fragmentlist.add(ScheduleFragment.getInstance())
        fragmentAdapter = FragmentCircularAdapter(childFragmentManager, fragmentlist)
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
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}