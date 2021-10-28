package com.xiaoneng.ss.module.activity

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.Constant
import kotlinx.android.synthetic.main.activity_persons.*

/**
 * @author Burning
 * @description:
 * @date :2021/6/6 3:02 PM
 */
class PersonsActivity : BaseActivity() {
    var text: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_persons
    }

    override fun initView() {
        super.initView()
        text = intent.getStringExtra(Constant.DATA)
        tvPersons.text = text
    }

    override fun initData() {
        super.initData()
    }

}