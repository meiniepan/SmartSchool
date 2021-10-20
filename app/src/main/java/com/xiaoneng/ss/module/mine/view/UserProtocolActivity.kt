package com.xiaoneng.ss.module.mine.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import kotlinx.android.synthetic.main.activity_user_protocol.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class UserProtocolActivity : BaseLifeCycleActivity<AccountViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_user_protocol


    override fun initView() {
        super.initView()
        webView.loadUrl("http://xnsaas.com/xieyi.html")
    }

    override fun initData() {
        super.initData()
    }


    override fun initDataObserver() {
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

}