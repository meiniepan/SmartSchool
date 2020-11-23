package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.initSiteTimes
import com.xiaoneng.ss.module.school.adapter.SiteItemAdapter
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel

/**
 * @author Burning
 * @description:场地预约详情
 * @date :2020/10/23 3:17 PM
 */
class AddBookSiteActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapter: SiteItemAdapter
    var mData:SiteBean? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_book_site
    }

    override fun initView() {
        super.initView()
        mData = intent.getParcelableExtra(Constant.DATA)
        mData?.let {

            initAdapter()
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getCanBookRooms(DateUtil.formatDateCustomDay(chosenDay!!))
    }

    private fun initAdapter() {
        var mSiteTimes = initSiteTimes()
        mAdapter = SiteItemAdapter(R.layout.item_site_item, mSiteTimes)
        var recyclerView = findViewById<RecyclerView>(R.id.rvSiteItem)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapter)
        }
        recyclerView.scrollToPosition(mData?.position!!)
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {

    }
}