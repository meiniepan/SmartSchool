package com.xiaoneng.ss.module.circular.adapter

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.getCornerRadii
import com.xiaoneng.ss.module.circular.model.ScheduleBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class EventSimpleAdapter(layoutId: Int, listData: MutableList<ScheduleBean>?) :
    BaseQuickAdapter<ScheduleBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: ScheduleBean) {
        viewHolder?.let { holder ->
            var str = ""
            str = if (item.title!!.length > 2 && holder.adapterPosition < 2) {
                item.title!!.substring(0, 2)
            } else {
                item.title ?: ""
            }


            var view = holder.getView<TextView>(R.id.tvBriefEvent)
            view.text = str


            val gd = GradientDrawable()
                gd.setColor(mContext.resources.getColor(R.color.themeColor))
//                gd.setColor(Color.parseColor(item.color))
            gd.cornerRadii = getCornerRadii(5f, 5f, 5f, 5f)
            view.background = gd
//            gd.setStroke(strokeWidth, strokeColor)

        }
    }
}