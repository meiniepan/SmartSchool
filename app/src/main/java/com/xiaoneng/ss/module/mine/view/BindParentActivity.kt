package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.ParentBean
import com.xiaoneng.ss.model.UnbindParentResp
import com.xiaoneng.ss.module.mine.adapter.ParentsAdapter
import kotlinx.android.synthetic.main.activity_bind_parent.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class BindParentActivity : BaseLifeCycleActivity<AccountViewModel>() {
    lateinit var mAdapter: ParentsAdapter
    var mData = ArrayList<ParentBean>()

    override fun getLayoutId(): Int = R.layout.activity_bind_parent


    override fun initView() {
        super.initView()
        initAdapter()
        tvAddParent.setOnClickListener {
            mStartActivity<AddParentActivity>(this)
        }
    }

    private fun initAdapter() {
        mAdapter = ParentsAdapter(R.layout.item_parents, mData)
        rvParent.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    private fun showDialog(phone: String?) {

        mAlert("解除后不可恢复请慎重选择是否解除与该家长的绑定", "是否确定解除该家长") {
            rvParent.showLoadingView()
            mViewModel.unbindParent(phone)
        }

    }


    override fun onResume() {
        super.onResume()
        mData.clear()
        UserInfo.getUserBean().parents?.let {
            mData.addAll(it)
        }
            rvParent.notifyDataSetChanged()
    }

    override fun initDataObserver() {
        mViewModel.mParentsData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<UnbindParentResp>(it)?.let {
                    it.parents?.let {

                        var userBean = UserInfo.getUserBean()
                        userBean.parents = it
                        UserInfo.modifyUserBean(userBean)
                        mData.clear()
                        mData.addAll(it)
                        rvParent.notifyDataSetChanged()
                    }
                }
            }
        })

    }


}