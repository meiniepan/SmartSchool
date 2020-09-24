package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.TimetableBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TitleTimetableAdapter(layoutId: Int, listData: MutableList<TimetableBean>) :
    BaseQuickAdapter<TimetableBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: TimetableBean) {
        viewHolder?.let { holder ->
           var textView = holder.getView<TextView>(R.id.tvNameWeek)
           var v = holder.getView<View>(R.id.vTimeTitle)
            holder.setText(R.id.tvNameWeek, item?.week)
            if (DateUtil.isSameDay(item.time.toLong() * 1000)) {
                holder.setTextColor(R.id.tvNameWeek,mContext.resources.getColor(R.color.titleBlack))
                textView.setTextSize(15f)
                v.visibility = View.VISIBLE
            } else {
                holder.setTextColor(R.id.tvNameWeek,mContext.resources.getColor(R.color.commonHint))
                textView.setTextSize(12f)
                v.visibility = View.GONE
            }
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)

        }
    }

}