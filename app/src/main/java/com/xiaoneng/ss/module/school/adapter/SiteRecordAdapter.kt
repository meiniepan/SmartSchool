package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
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

    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->
            var tvRemark = holder.getView<TextView>(R.id.tvSiteRecordRemark)
            var tvCancel = holder.getView<TextView>(R.id.tvSiteRecordCancel)
            holder.addOnClickListener(R.id.tvSiteRecordCancel)
            var timeStr = ""
            item.oetime?.let {
                if (it.length > 5) {
                    timeStr = item.ostime + "-" + it.substring(it.length - 5, it.length)
                }
            }
            if (item.remark.isNullOrEmpty()) {
                tvRemark.visibility = View.GONE
            } else {
                tvRemark.visibility = View.VISIBLE
            }
            holder.setText(R.id.tvSiteItemRoomName, item.classroomname)
            holder.setText(R.id.tvSiteItemRoomAddress, item.classroom?.addr)
            holder.setText(R.id.tvSiteItemRoomCapacity, item.classroom?.addr)
            holder.setText(R.id.tvSiteItemRoomEquip, item.classroom?.addr)
                .setText(R.id.tvSiteRecordTime, timeStr)
                .setText(R.id.tvSiteRecordRemark, item.remark)
        }
    }


}