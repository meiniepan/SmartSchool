package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.model.SysMsgBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.school.adapter.SysMsgAdapter
import kotlinx.android.synthetic.main.fragment_notice.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class SystemMsgListActivity : BaseLifeCycleActivity<CircularViewModel>(), View.OnClickListener {
    lateinit var mAdapter: SysMsgAdapter
    var mData=ArrayList<SysMsgBean>()
    override fun getLayoutId(): Int {
        return R.layout.activity_system_msg
    }

    override fun initView() {
        super.initView()
        initAdapter()
    }

    override fun initData() {
        super.initData()
    }

    private fun initAdapter() {
        mAdapter = SysMsgAdapter(R.layout.item_sys_msg,mData)
        rvNotice.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context,20f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mStartActivity<NoticeDetailActivity>(this){
                putExtra(Constant.TITLE,mData[position].title)
                putExtra(Constant.ID,mData[position].id)
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