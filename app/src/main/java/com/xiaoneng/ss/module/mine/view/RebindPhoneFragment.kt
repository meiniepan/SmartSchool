package com.xiaoneng.ss.module.mine.view

import android.view.View
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_rebind.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class RebindPhoneFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    var showRebindLayout: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_rebind

    companion object {
        fun getInstance(): RebindPhoneFragment? {
            return RebindPhoneFragment()
        }

    }

    override fun initView() {
        super.initView()
        tvCurrentPhone.text = UserInfo.getUserBean().phone
        ivNext.setOnClickListener {
            llCurrent.visibility = View.GONE
            llRebind.visibility = View.VISIBLE
        }
        tvConfirmSecure.setOnClickListener {

        }
    }


    override fun initDataObserver() {

    }


}