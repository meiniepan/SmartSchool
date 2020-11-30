package com.xiaoneng.ss.module.school.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SiteItemBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class RoomBookRecordAdapter(layoutId: Int, listData: MutableList<SiteItemBean>) :
    BaseQuickAdapter<SiteItemBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: SiteItemBean) {
        viewHolder.let { holder ->
            var tvAction = holder.getView<TextView>(R.id.tvAction)
            holder.addOnClickListener(R.id.tvAction)
            var timeStr = ""
            item.oetime?.let {
                if (it.length > 5) {
                    timeStr = item.ostime + "-" + it.substring(it.length - 5, it.length)
                }
            }

            holder.setText(R.id.tvRoomBookerName, item.operator?.realname)
            holder.setText(R.id.tvRoomBookTime, timeStr)
            holder.setText(R.id.tvRoomBookStatus, item.id)
            holder.setText(R.id.tvRoomBookTheme, item.remark)
        }
    }


}