package com.xiaoneng.ss.module.sys.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.sys.adapter.FragmentCircularAdapter
import com.xiaoneng.ss.module.sys.viewmodel.CircularViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class CircularFragment : BaseLifeCycleFragment<CircularViewModel>() {
    lateinit var fragmentAdapter: FragmentCircularAdapter
    lateinit var fragmentlist: ArrayList<Fragment>

    companion object {
        fun getInstance(): CircularFragment? {
            return CircularFragment()
        }
    }

    override fun initView() {
        super.initView()
        fragmentAdapter = FragmentCircularAdapter(this,fragmentlist)
    }
    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_circular


}