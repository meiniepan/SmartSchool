package com.xiaoneng.ss.account.view

import android.view.View
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.startActivity
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

            }
            R.id.iv_item_tea -> {

            }
            R.id.iv_item_stu -> {
                startActivity<LoginStuActivity>(this)
            }
        }
    }
}