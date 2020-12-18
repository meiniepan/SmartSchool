package com.xiaoneng.ss.module.school.adapter

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
import com.xiaoneng.ss.module.school.view.RoomBookRecordsActivity


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
            holder.setText(R.id.tvSiteItemRoomName, item.classroomname)
                .setText(R.id.tvSiteItemRoomAddress, item.addr)
                .setText(R.id.tvSiteItemRoomCapacity, item.total)
                .setText(R.id.tvSiteItemRoomEquip, item.remark)
            initAdapter(holder, item)
        }
    }

    private fun initAdapter(holder: BaseViewHolder, item: SiteBean) {
        var mAdapter: SiteItemAdapter = SiteItemAdapter(R.layout.item_site_item, null)
        var mRecycler: RecyclerView = holder.getView(R.id.rvSiteItem)
        if (!recyclerViews.contains(mRecycler)) {
            mRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mAdapter
            }
            if (mP > -1) {
                mRecycler.scrollToPosition(mP)
            }
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
        var canBook: List<String>? = item.book_position?.split(",")
        if (canBook != null && canBook.isNotEmpty()) {
            canBook.forEach {
                mSiteData[it.toIntSafe()].isBooked = false
            }
        } else {
            mSiteData.forEach {
                it.isBooked = false
            }
        }

        item.books?.forEach {
            for (i in it.os_position!!.toIntSafe()..it.oe_position!!.toIntSafe()) {
                mSiteData[i].isBooked = true
            }
        }
        for (i in 0 until mSiteData.size) {
            if (i < mP) {
                mSiteData[i].isBooked = true
            }
        }


        mAdapter.setNewData(mSiteData)
        mAdapter.setOnItemClickListener { _, view, position ->
            item.position = position
            if (mSiteData[position].isBooked) {
                item.startType = 0
                mStartActivity<RoomBookRecordsActivity>(mContext) {
                    putExtra(Constant.DATA, item.books)
                }

            } else {
                item.startType = 1
                mStartActivity<AddBookSiteActivity>(mContext) {
                    item.books = mSiteData
                    putExtra(Constant.DATA, item)
                }
            }
        }
    }

    fun setPosition(position: Int) {
        this.mP = position
    }


}