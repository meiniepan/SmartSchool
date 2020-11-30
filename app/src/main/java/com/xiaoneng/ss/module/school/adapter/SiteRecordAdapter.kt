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
            holder.addOnClickListener(R.id.tvAction)
            var tvAction = holder.getView<TextView>(R.id.tvAction)
            var tvRemark = holder.getView<TextView>(R.id.tvRecordBookTheme)
            var statusStr = ""
            tvAction.visibility = View.GONE
            if (item.status == "-1") {
                statusStr = "已取消"
            } else if (item.status == "0") {
                statusStr = "未开始"
                tvAction.visibility = View.VISIBLE
            } else if (item.status == "1") {
                statusStr = "进行中"
            } else if (item.status == "2") {
                statusStr = "已结束"
            } else if (item.status == "3") {
                statusStr = "被占用"
            }
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
            holder.setText(R.id.tvRecordBookName, item.classroom?.classroomname)
            holder.setText(R.id.tvRecordBookTime, timeStr)
            holder.setText(R.id.tvRecordBookStatus, statusStr)
            holder.setText(R.id.tvRecordBookTheme, item.remark)
                .setText(R.id.tvRecordBookAddress, item.classroom?.addr)
        }
    }


}