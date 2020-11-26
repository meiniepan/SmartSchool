package com.xiaoneng.ss.module.school.adapter

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.initSiteTimes
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.toIntSafe
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.view.AddBookSiteActivity


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class SiteAdapter(layoutId: Int, listData: MutableList<SiteBean>) :
    BaseQuickAdapter<SiteBean, BaseViewHolder>(layoutId, listData) {
    private var mP: Int = -1
    var recyclerViews = ArrayList<RecyclerView>()
    var mScroll: RecyclerView? = null


    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->
            var tvRoomName = holder.getView<TextView>(R.id.tvSiteItemRoomName)
            tvRoomName.text = item.classroomname
            initAdapter(holder, item)
        }
    }

    private fun initAdapter(holder: BaseViewHolder, item: SiteBean) {
        lateinit var mAdapter: SiteItemAdapter
        var mRecycler: RecyclerView = holder.getView(R.id.rvSiteItem)
        if (!recyclerViews.contains(mRecycler)) {
            recyclerViews.add(mRecycler)
        }
        mRecycler.clearOnScrollListeners()
        mRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (mScroll == null || mScroll == recyclerView) {
                    mScroll = recyclerView
                    recyclerViews.forEach {
                        if (it != recyclerView) {
                            it.scrollBy(dx, dy)
                        }
                    }
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mScroll = null
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mScroll = recyclerView
                }
            }
        })
        var mSiteData = initSiteTimes()
        item.books?.forEach {
            for (i in it.os_position!!.toIntSafe()..it.oe_position!!.toIntSafe()) {
                mSiteData[i].isBooked = true
            }
        }

        mAdapter = SiteItemAdapter(R.layout.item_site_item, mSiteData)

        mRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        if (mP > -1) {
            mRecycler.scrollToPosition(mP)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            item.position = position
            mStartActivity<AddBookSiteActivity>(mContext) {
                if (mSiteData[position].isBooked) {
                    item.startType = 0
                } else {
                    item.startType = 1
                }
                putExtra(Constant.DATA, item)
            }
        }
    }

    fun setPosition(position: Int) {
        this.mP = position
    }


}