package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.sys.adapter.FragmentCircularAdapter
import kotlinx.android.synthetic.main.fragment_circular.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class CircularFragment : BaseLifeCycleFragment<CircularViewModel>() {
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
        tvCircular.setOnClickListener{
            vpCircular.setCurrentItem(0,true)
        }
        tvSchedule.setOnClickListener{
            vpCircular.setCurrentItem(1,true)
        }
    }

    private fun initViewPager() {
        fragmentlist.add(ScheduleFragment1.getInstance())
        fragmentlist.add(ScheduleFragment2.getInstance())
        fragmentAdapter = FragmentCircularAdapter(childFragmentManager, fragmentlist)
        vpCircular.adapter = fragmentAdapter
    }

    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}