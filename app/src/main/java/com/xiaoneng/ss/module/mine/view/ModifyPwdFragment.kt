package com.xiaoneng.ss.module.mine.view

import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_modify_pwd.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ModifyPwdFragment : BaseLifeCycleFragment<SchoolViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_modify_pwd

    companion object {
        fun getInstance(): Fragment {
            return ModifyPwdFragment()
        }

    }

    override fun initView() {
        super.initView()

        tvConfirmModifyPwd.setOnClickListener {

        }
    }


    override fun initDataObserver() {

    }


}