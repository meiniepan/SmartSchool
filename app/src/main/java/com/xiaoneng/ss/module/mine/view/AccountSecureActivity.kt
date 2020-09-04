package com.xiaoneng.ss.module.mine.view

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_account_secure.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AccountSecureActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_account_secure


    override fun initView() {
        super.initView()
        initViewPager()
        initTab()

    }

    override fun initData() {
        super.initData()
//        mViewModel.getNoticeList()

    }

    private fun initTab() {
        tvSecurityTab1.setOnClickListener {
            checkFirsTab()
        }
        tvSecurityTab2.setOnClickListener {
            checkSecondTab()
        }
        
    }

    private fun checkFirsTab() {
        tvSecurityTab1.setChecked(true)
        tvSecurityTab2.setChecked(false)
        
        vpSecurity.setCurrentItem(0, true)
    }

    private fun checkSecondTab() {
        tvSecurityTab2.setChecked(true)
        tvSecurityTab1.setChecked(false)
        
        vpSecurity.setCurrentItem(1, true)
    }
    

    private fun initViewPager() {
        fragmentList.add(RebindPhoneFragment.getInstance())
        fragmentList.add(ModifyPwdFragment.getInstance())
        fragmentAdapter = FragmentVpAdapter(
            supportFragmentManager,
            fragmentList
        )
        vpSecurity.adapter = fragmentAdapter
        vpSecurity.addOnPageChangeListener(object : OnPageChangeListener {
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

    }


}