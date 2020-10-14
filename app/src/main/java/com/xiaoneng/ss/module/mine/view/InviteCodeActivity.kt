package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.mine.model.InviteCodeBean
import kotlinx.android.synthetic.main.activity_my_invite.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class InviteCodeActivity : BaseLifeCycleActivity<AccountViewModel>() {
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<InviteCodeBean>()

    override fun getLayoutId(): Int = R.layout.activity_my_invite


    override fun initView() {
        super.initView()
        initAdapter()

    }

    override fun initData() {
        super.initData()
        mViewModel.queryCodeList()
    }


    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        rvInviteCode.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initDataObserver() {
        mViewModel.mParentsData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<InviteCodeBean>>(it)?.let {
                    mData.clear()
                    mData.addAll(it)
                    rvInviteCode.notifyDataSetChanged()
                }
            }
        })

    }


}