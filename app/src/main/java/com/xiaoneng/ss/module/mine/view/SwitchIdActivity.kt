package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import kotlinx.android.synthetic.main.activity_my_invite.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class SwitchIdActivity : BaseLifeCycleActivity<CircularViewModel>() {
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.activity_switch_id


    override fun initView() {
        super.initView()
        initAdapter()

    }

    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        contentLayout.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 82f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getNoticeList()
        mData.add(NoticeBean(id =""))
        mData.add(NoticeBean(id =""))
    }


    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    showEmpty()
                }
            }
        })

    }


}