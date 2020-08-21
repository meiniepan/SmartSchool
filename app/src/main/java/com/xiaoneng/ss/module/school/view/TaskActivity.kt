package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.circular.adapter.FragmentCircularAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.view.NoticeFragment
import com.xiaoneng.ss.module.circular.view.ScheduleFragment
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.fragment_circular.vpCircular

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class TaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var fragmentAdapter: FragmentCircularAdapter
    var fragmentList = ArrayList<Fragment>()
    var mData=ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.activity_task

    companion object {
        fun getInstance(): TaskActivity? {
            return TaskActivity()
        }

    }

    override fun initView() {
        super.initView()
        initViewPager()
        initTab()

    }

    override fun initData() {
        super.initData()
        mViewModel.getNoticeList()

    }

    private fun initTab() {
        tvTaskTab1.setOnClickListener {
            checkFirsTab()
        }
        tvTaskTab2.setOnClickListener {
            checkSecondTab()
        }
        tvTaskTab3.setOnClickListener {
            checkSecondTab()
        }
        tvTaskTab4.setOnClickListener {
            checkSecondTab()
        }
    }

    private fun checkSecondTab() {
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(true)
        vpCircular.setCurrentItem(1, true)
        setStatusBarDark()
    }

    private fun checkFirsTab() {
        tvTaskTab1.setChecked(true)
        tvTaskTab2.setChecked(false)
        vpCircular.setCurrentItem(0, true)
        setStatusBarDark()
    }

    private fun initViewPager() {
        fragmentList.add(NoticeFragment.getInstance())
        fragmentList.add(ScheduleFragment.getInstance())
        fragmentAdapter = FragmentCircularAdapter(supportFragmentManager, fragmentList)
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
                mData.clear()
                for (i in it.data){
                    if (i.type == "system")
                        mData.add(i)
                }
            }
        })
    }


}