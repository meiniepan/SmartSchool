package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.adapter.SiteAdapter
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteItemBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_book_site.*

/**
 * @author Burning
 * @description:场地预约
 * @date :2020/10/23 3:17 PM
 */
class BookSiteActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: SiteAdapter
    var mData: ArrayList<SiteBean> = ArrayList()
    var recyclerViews = ArrayList<RecyclerView>()

    override fun getLayoutId(): Int {
        return R.layout.activity_book_site
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
        var bean = SiteItemBean()
        var bean2 = SiteBean()
        var beans = ArrayList<SiteItemBean>()
        var beans2 = ArrayList<SiteBean>()
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        beans.add(bean)
        bean2.list = beans
        mData.add(bean2)
        mData.add(bean2)
        mData.add(bean2)
        mData.forEach {
            recyclerViews.add(RecyclerView(this))
        }
        rvSite.notifyDataSetChanged()
    }

    private fun initAdapter() {
        mAdapter = SiteAdapter(R.layout.item_site, mData)
        rvSite.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookSiteActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {

    }
}