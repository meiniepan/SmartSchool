package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteItemBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class SiteAdapter(layoutId: Int, listData: MutableList<SiteBean>) :
    BaseQuickAdapter<SiteBean, BaseViewHolder>(layoutId, listData) {
    var recyclerViews = ArrayList<RecyclerView>()
     var mScroll:RecyclerView? = null

    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->

            initAdapter(holder, item)
        }
    }

    private fun initAdapter(holder: BaseViewHolder, item: SiteBean) {
        lateinit var mAdapter: SiteItemAdapter
        var mRecycler: RecyclerView = holder.getView(R.id.rvSiteItem)
        recyclerViews.add(mRecycler)

        mRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mScroll==null||mScroll==recyclerView){
                mScroll = recyclerView
                recyclerViews.forEach {
                    if (it != recyclerView) {
                        it.scrollBy(dx, dy)
                    }
                }}
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    mScroll = null
                }else if (newState==RecyclerView.SCROLL_STATE_DRAGGING){
                    mScroll = recyclerView
                }
            }
        })
        var mSiteData: ArrayList<SiteItemBean> = ArrayList()
        mSiteData.addAll(item.list)

        mAdapter = SiteItemAdapter(R.layout.item_site_item, mSiteData)

        mRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


}