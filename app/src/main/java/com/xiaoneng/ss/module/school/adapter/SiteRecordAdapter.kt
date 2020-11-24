package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SiteBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class SiteRecordAdapter(layoutId: Int, listData: MutableList<SiteBean>) :
    BaseQuickAdapter<SiteBean, BaseViewHolder>(layoutId, listData) {
    var recyclerViews = ArrayList<RecyclerView>()
    var mScroll: RecyclerView? = null

    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->
            var timeStr = ""
            item.oetime?.let {
                if (it.length > 5) {
                    timeStr = item.ostime + "-" + it.substring(it.length - 5, it.length)
                }
            }
            var tvRoomName = holder.getView<TextView>(R.id.tvSiteItemRoomName)
            var tvRemark = holder.getView<TextView>(R.id.tvSiteRecordRemark)
            if (item.remark.isNullOrEmpty()) {
                tvRemark.visibility = View.GONE
            } else {
                tvRemark.visibility = View.VISIBLE
            }
            holder.setText(R.id.tvSiteItemRoomName, item.roomname)
                .setText(R.id.tvSiteRecordTime, timeStr)
                .setText(R.id.tvSiteRecordRemark, item.remark)
        }
    }


}