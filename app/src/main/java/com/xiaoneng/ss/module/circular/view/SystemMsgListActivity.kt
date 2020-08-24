package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.adapter.SysMsgAdapter
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_system_msg.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class SystemMsgListActivity : BaseLifeCycleActivity<CircularViewModel>(), View.OnClickListener {
    lateinit var mAdapter: SysMsgAdapter
    var mData: ArrayList<NoticeBean>? = ArrayList()
    override fun getLayoutId(): Int {
        return R.layout.activity_system_msg
    }

    override fun initView() {
        super.initView()
        mData = intent.getParcelableArrayListExtra<NoticeBean>(Constant.DATA)
        mData?.let {
            if (mData!!.size > 0) {
                initAdapter()
            } else {
                showEmpty()
            }
        }
    }

    override fun initData() {
        super.initData()
    }

    private fun initAdapter() {

        mAdapter = SysMsgAdapter(R.layout.item_sys_msg, mData)
        rvSysMsg.apply {
            layoutManager = LinearLayoutManager(this@SystemMsgListActivity)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 20f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mStartActivity<NoticeDetailActivity>(this) {
                putExtra(Constant.TITLE, mData!![position].title)
                putExtra(Constant.ID, mData!![position].id)
            }
        }
    }

    override fun initDataObserver() {

    }

    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.tvSwitchId -> {
////                mStartActivity<LoginSwitchActivity>(this){
////                    putExtra("title",md)
////                }
//            }
//
//        }
    }
}