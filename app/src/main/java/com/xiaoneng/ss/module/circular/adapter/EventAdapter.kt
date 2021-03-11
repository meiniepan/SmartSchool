package com.xiaoneng.ss.module.circular.adapter

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.ScheduleBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class EventAdapter(layoutId: Int, listData: MutableList<ScheduleBean>) :
    BaseQuickAdapter<ScheduleBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: ScheduleBean) {
        viewHolder?.let { holder ->
            var timeB = ""
            var timeB2 = ""
            var timeE = ""
            var timeE2 = ""
            if (item.scheduletime!!.length >= 5) {
                timeB = item.scheduletime!!.substring(
                    item.scheduletime!!.length - 5,
                    item.scheduletime!!.length
                )
                timeB2 = item.scheduletime!!.substring(
                    5,
                    item.scheduletime!!.length - 5
                )
            } else {
                timeB = item.scheduletime!!
            }
            if (item.scheduleover!!.length >= 5) {
                timeE = item.scheduleover!!.substring(
                    item.scheduleover!!.length - 5,
                    item.scheduleover!!.length
                )
                timeE2 = item.scheduleover!!.substring(
                    5,
                    item.scheduleover!!.length - 5
                )
            } else {
                timeE = item.scheduleover!!
            }
            var timeStr = ""
            if (timeB2 != timeE2) {
                 timeStr = item.scheduletime!!.substring(
                     5,
                     item.scheduletime!!.length
                 ) + "~".plus(item.scheduleover!!.substring(
                     5,
                     item.scheduleover!!.length
                 ))
            } else {
                 timeStr = timeB + "~".plus(timeE)
            }

            var remarkStr = "" + item.remark
            if (item.remark.isNullOrEmpty()) {
                holder.getView<View>(R.id.llRemarkScheduleItem).visibility = View.GONE
            } else {
                holder.getView<View>(R.id.llRemarkScheduleItem).visibility = View.VISIBLE
                holder.getView<TextView>(R.id.tvRemark5).text = remarkStr
            }

            holder.setText(R.id.tvTitle5, item?.title)
                .setText(R.id.tvTime5, timeStr)

            var shape = ShapeDrawable(OvalShape())
            if (!TextUtils.isEmpty(item.color)) {

                shape.paint.color = Color.parseColor(item.color!!)
            }
            var view = holder.getView<View>(R.id.vOval5)
            view.background = shape
//            if (item?.isCheck!!){
//                holder.setBackgroundRes(R.id.tvDay2,R.drawable.bac_blue_oval)
//                holder.setTextColor(R.id.tvDay2,mContext.resources.getColor(R.color.white))
//            }else{
//                holder.setBackgroundRes(R.id.tvDay2,0)
//                holder.setTextColor(R.id.tvDay2,mContext.resources.getColor(R.color.titleBlack))
//            }
        }
    }
}