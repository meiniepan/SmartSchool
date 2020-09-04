package com.xiaoneng.ss.module.mine.view

import android.content.DialogInterface
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.dialog.DialogUtil
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.model.ParentBean
import com.xiaoneng.ss.module.mine.adapter.ParentsAdapter
import kotlinx.android.synthetic.main.activity_bind_parent.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
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

    private fun showDialog(phone: String) {

        DialogUtil.showDialogTwoButton(this,
            "是否确定解除该家长",
            "解除后不可恢复请慎重选择是否解除与该家长的绑定",
            DialogInterface.OnClickListener { _, _ ->
                mViewModel.unbindParent(phone)

            }
        )
    }


    override fun onResume() {
        super.onResume()
        mData.clear()
        UserInfo.getUserBean().parents?.let {
            mData.addAll(it)
        }
        if (mData.size > 0) {
            rvParent.notifyDataSetChanged()
        } else {
            rvParent.showEmptyView()
        }
    }

    override fun initDataObserver() {
        mViewModel.mParentsData.observe(this, Observer { response ->
            response?.let {
//                mData.clear()
//                mData.addAll(it.data)
//                if (mData.size > 0) {
//                    rvParent.notifyDataSetChanged()
//                } else {
//                    rvParent.showEmptyView()
//                }
            }
        })

    }


}