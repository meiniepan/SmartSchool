package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.mToast
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.SiteRecordAdapter
import com.xiaoneng.ss.module.school.model.AddBookSiteBody
import com.xiaoneng.ss.module.school.model.BookSiteRecordsResp
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_book_site_records.*

/**
 * @author Burning
 * @description:场地预约
 * @date :2020/10/23 3:17 PM
 */
class BookSiteRecordsActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: SiteRecordAdapter
    var mData: ArrayList<SiteBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_book_site_records
    }

    override fun initView() {
        super.initView()
        initAdapter()

    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mData.clear()
        rvSiteRecords.showLoadingView()
        mViewModel.getBookSiteRecord()
    }

    private fun initAdapter() {
        mAdapter = SiteRecordAdapter(R.layout.item_site_record, mData)
        rvSiteRecords.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookSiteRecordsActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tvAction)
            {
                showLoading()
                var sourceBean = mData[position]
                var bean = AddBookSiteBody(
                    token = UserInfo.getUserBean().token,
                    id = sourceBean.id,
                    status = "-1"
                )
                mViewModel.modifyBookSite(bean)
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<BookSiteRecordsResp>(it)?.let {
                    it.data?.let { it1 ->
                        mData.addAll(it1)
                        rvSiteRecords.notifyDataSetChanged()
                    }
                }
            }
        })

        mViewModel.mModifyBookSiteData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                getData()
            }
        })
    }
}