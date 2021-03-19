package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteItemBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class RoomBookRecordAdapter(layoutId: Int, listData: MutableList<SiteBean>) :
    BaseQuickAdapter<SiteBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: SiteBean) {
        viewHolder.let { holder ->
            var tvAction = holder.getView<TextView>(R.id.tvAction)
            holder.addOnClickListener(R.id.tvAction)
            tvAction.visibility = View.GONE
            var statusStr = ""
            if (item.status == "-1") {
                statusStr = "已取消"
            } else if (item.status == "0") {
                if (AppInfo.checkRule2("admin/spacebook/adlistRooms")){
                    tvAction.visibility = View.VISIBLE
                }
                statusStr = "未开始"
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

            holder.setText(R.id.tvRoomBookerName, item.operator?.realname)
            holder.setText(R.id.tvRoomBookTime, timeStr)
            holder.setText(R.id.tvRoomBookStatus, statusStr)
            holder.setText(R.id.tvRoomBookTheme, item.remark)
                .setText(R.id.tvRoomBookRoomName, item.classroomname)
        }
    }


}