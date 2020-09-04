package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.mine.model.InviteCodeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_task.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ThingLeaveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<InviteCodeBean>()


    override fun getLayoutId(): Int = R.layout.activity_thing_leave


    override fun initView() {
        super.initView()


    }




    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 82f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
//        mViewModel.mNoticeData.observe(this, Observer { response ->
//            response?.let {
//                mData.clear()
//                mData.addAll(it.data)
//                if (mData.size > 0) {
//                    mAdapter.notifyDataSetChanged()
//                } else {
//                    showEmpty()
//                }
//            }
//        })

    }


}