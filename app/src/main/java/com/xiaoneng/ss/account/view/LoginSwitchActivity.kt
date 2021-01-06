package com.xiaoneng.ss.account.view

import android.view.View
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import kotlinx.android.synthetic.main.activity_login_switch.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 6:51 PM
 */
class LoginSwitchActivity :BaseActivity() ,View.OnClickListener{

    override fun getLayoutId()= R.layout.activity_login_switch
    override fun initView() {
        super.initView()
        iv_item_par.setOnClickListener(this)
        iv_item_tea.setOnClickListener(this)
        iv_item_stu.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_item_par -> {
                mStartActivity<LoginTeacherActivity>(this){
                    putExtra(Constant.FLAG,false)
                }
            }
            R.id.iv_item_tea -> {
                mStartActivity<LoginTeacherActivity>(this)
            }
            R.id.iv_item_stu -> {
                mStartActivity<LoginStuActivity>(this)
            }
        }
    }

}