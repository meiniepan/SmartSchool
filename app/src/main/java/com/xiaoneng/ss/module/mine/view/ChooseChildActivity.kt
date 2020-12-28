package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.mine.adapter.ChooseChildAdapter
import kotlinx.android.synthetic.main.activity_choose_child.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ChooseChildActivity : BaseLifeCycleActivity<AccountViewModel>() {
    private var uid: String = ""
    lateinit var mAdapter: ChooseChildAdapter
    var mData = ArrayList<StudentBean>()

    override fun getLayoutId(): Int = R.layout.activity_choose_child


    override fun initView() {
        super.initView()
        initAdapter()
        tvConfirm.setOnClickListener {
            mViewModel.switchChild(uid)
        }

    }

    private fun initAdapter() {
        mAdapter = ChooseChildAdapter(R.layout.item_choose_child, mData)
        rvChooseChild.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            uid = mData[position].uid
            for (i in mData) {
                i.choice = "0"
            }
            mData[position].choice = "1"
            mAdapter.notifyDataSetChanged()
        }
    }


    override fun onResume() {
        super.onResume()
        mData.clear()
        UserInfo.getUserBean().students?.let {
            mData.addAll(it)
        }
            rvChooseChild.notifyDataSetChanged()
    }


    override fun initDataObserver() {
        mViewModel.mChildData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                netResponseFormat<UserBean>(it)?.let {
                    mToast("切换成功")
                    UserInfo.loginSuccess(it)
                    finish()
                }
            }
        })

    }


}