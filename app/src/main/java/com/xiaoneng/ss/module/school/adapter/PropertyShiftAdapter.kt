package com.xiaoneng.ss.module.school.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.PropertyTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/14
 * Time: 17:32
 */
class PropertyShiftAdapter(layoutId: Int, listData: MutableList<PropertyTypeBean>?) :
    BaseQuickAdapter<PropertyTypeBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder?, item: PropertyTypeBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvShift, item.name)

            if (item.checked) {
                holder.getView<TextView>(R.id.tvShift).apply {
                    setBackgroundResource(R.drawable.bac_blue_bac_19)
                    setTextColor(mContext.resources.getColor(R.color.white))
                }

            } else {
                holder.getView<TextView>(R.id.tvShift).apply {
                    setBackgroundResource(R.drawable.bac_blue_line_19)
                    setTextColor(mContext.resources.getColor(R.color.themeColor))
                }
            }
        }
    }

}