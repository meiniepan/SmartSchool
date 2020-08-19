package com.xiaoneng.ss.module.circular.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.SchoolViewModel
import com.xiaoneng.ss.module.sys.adapter.SchoolAdapter
import kotlinx.android.synthetic.main.fragment_notice.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class NoticeFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    lateinit var mAdapter: SchoolAdapter
     var mData=ArrayList<NoticeBean>()

    override fun getLayoutId(): Int = R.layout.fragment_notice

    companion object {
        fun getInstance(): Fragment {
            return NoticeFragment()
        }

    }

    override fun initView() {
        super.initView()
        initAdapter()

    }

    private fun initAdapter() {
        mAdapter = SchoolAdapter(R.layout.item_notice,mData)
        rvNotice.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context,dp2px(context,10f).toInt()))
            adapter = mAdapter
        }
    }


    override fun initData() {
        super.initData()
        mViewModel.getNoticeList()
    }


    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.data)
                mAdapter.notifyDataSetChanged()
            }
        })
    }


}