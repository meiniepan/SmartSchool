package com.xiaoneng.ss.module.mine.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.mine.adapter.MineAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    protected lateinit var mAdapter: MineAdapter

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun getInstance(): MineFragment? {
            return MineFragment()
        }

    }

    override fun initView() {
        super.initView()
        displayImage(requireContext(), UserInfo.getUserBean().portrait, ivAvatarMine)
        tvNameMine.text = UserInfo.getUserBean().realname

        llItem1.setOnClickListener {
            mStartActivity<MineInfoActivity>(requireContext())
        }
    }

    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}