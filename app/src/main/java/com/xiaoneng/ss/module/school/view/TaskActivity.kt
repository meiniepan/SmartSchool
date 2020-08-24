package com.xiaoneng.ss.module.school.view

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.adapter.FragmentCircularAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
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
    private lateinit var fragmentAdapter: FragmentCircularAdapter
    private var fragmentList = ArrayList<Fragment>()
    var mData = ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.activity_task


    override fun initView() {
        super.initView()
        initViewPager()
        initTab()
        ivAddTask.setOnClickListener {
            mStartActivity<AddTaskActivity>(this)
        }

    }

    override fun initData() {
        super.initData()
//        mViewModel.getNoticeList()

    }

    private fun initTab() {
        tvTaskTab1.setOnClickListener {
            checkFirsTab()
        }
        tvTaskTab2.setOnClickListener {
            checkSecondTab()
        }
        tvTaskTab3.setOnClickListener {
            checkThirdTab()
        }
        tvTaskTab4.setOnClickListener {
            check4Tab()
        }
    }

    private fun checkFirsTab() {
        tvTaskTab1.setChecked(true)
        tvTaskTab2.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab4.setChecked(false)
        vpCircular.setCurrentItem(0, true)
        setStatusBarDark()
    }

    private fun checkSecondTab() {
        tvTaskTab2.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab4.setChecked(false)
        vpCircular.setCurrentItem(1, true)
        setStatusBarDark()
    }

    private fun checkThirdTab() {
        tvTaskTab3.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab4.setChecked(false)
        vpCircular.setCurrentItem(2, true)
        setStatusBarDark()
    }

    private fun check4Tab() {
        tvTaskTab4.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab3.setChecked(false)
        vpCircular.setCurrentItem(3, true)
        setStatusBarDark()
    }

    private fun initViewPager() {
        fragmentList.add(TaskStatusFragment.getInstance())
        fragmentList.add(TaskStatusFragment.getInstance())
        fragmentList.add(TaskStatusFragment.getInstance())
        fragmentList.add(TaskStatusFragment.getInstance())
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
                } else if (position == 2) {
                    checkThirdTab()
                } else if (position == 3) {
                    check4Tab()
                }
            }
        })
    }

    override fun initDataObserver() {

    }


}