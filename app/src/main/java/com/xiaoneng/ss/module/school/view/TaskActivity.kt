package com.xiaoneng.ss.module.school.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.fragment_circular.vpCircular

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class TaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_task


    override fun initView() {
        super.initView()
        if (UserInfo.getUserBean().usertype == "2" || UserInfo.getUserBean().usertype == "99") {
            ivAddTask.visibility = View.VISIBLE
            llTab1.visibility = View.VISIBLE
            llTab2.visibility = View.GONE
        } else {
            ivAddTask.visibility = View.GONE
            llTab1.visibility = View.GONE
            llTab2.visibility = View.VISIBLE
        }
        initViewPager1()
        initViewPager2()
        initTab1()
        initTab2()
        ivAddTask.setOnClickListener {
            mStartActivity<AddTaskActivity>(this)
        }

    }


    private fun initTab1() {
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
        tvTaskTab5.setOnClickListener {
            check5Tab()
        }
        tvTaskTab6.setOnClickListener {
            check6Tab()
        }
    }

    private fun initTab2() {
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
        tvTaskTab5.setChecked(false)
        tvTaskTab6.setChecked(false)
        vpCircular.setCurrentItem(0, true)
    }

    private fun checkSecondTab() {
        tvTaskTab2.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab4.setChecked(false)
        tvTaskTab5.setChecked(false)
        tvTaskTab6.setChecked(false)
        vpCircular.setCurrentItem(1, true)
    }

    private fun checkThirdTab() {
        tvTaskTab3.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab4.setChecked(false)
        tvTaskTab5.setChecked(false)
        tvTaskTab6.setChecked(false)
        vpCircular.setCurrentItem(2, true)
    }

    private fun check4Tab() {
        tvTaskTab4.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab5.setChecked(false)
        tvTaskTab6.setChecked(false)
        vpCircular.setCurrentItem(3, true)
    }

    private fun check5Tab() {
        tvTaskTab5.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab4.setChecked(false)
        tvTaskTab6.setChecked(false)
        vpCircular.setCurrentItem(4, true)
    }

    private fun check6Tab() {
        tvTaskTab6.setChecked(true)
        tvTaskTab1.setChecked(false)
        tvTaskTab2.setChecked(false)
        tvTaskTab3.setChecked(false)
        tvTaskTab4.setChecked(false)
        tvTaskTab5.setChecked(false)
        vpCircular.setCurrentItem(5, true)
    }

    private fun check21Tab() {
        tvTaskTab21.setChecked(true)
        tvTaskTab22.setChecked(false)
        tvTaskTab23.setChecked(false)
        vpCircular.setCurrentItem(0, true)
    }

    private fun check22Tab() {
        tvTaskTab21.setChecked(false)
        tvTaskTab22.setChecked(true)
        tvTaskTab23.setChecked(false)
        vpCircular.setCurrentItem(1, true)
    }

    private fun check23Tab() {
        tvTaskTab21.setChecked(false)
        tvTaskTab22.setChecked(false)
        tvTaskTab23.setChecked(true)
        vpCircular.setCurrentItem(2, true)
    }

    private fun initViewPager1() {
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "-1") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "1") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "0") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "2") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "3") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "4") }
        })
        fragmentAdapter = FragmentVpAdapter(
            supportFragmentManager,
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
                } else if (position == 2) {
                    checkThirdTab()
                } else if (position == 3) {
                    check4Tab()
                }
            }
        })
    }

    private fun initViewPager2() {
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "-1") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "3") }
        })
        fragmentList.add(TaskStatusFragment.getInstance().apply {
            arguments = Bundle().apply { putString(Constant.TASK_STATUS, "4") }
        })

        fragmentAdapter = FragmentVpAdapter(
            supportFragmentManager,
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