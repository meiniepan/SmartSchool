package com.xiaoneng.ss.module.circular.adapter

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.ScheduleBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class EventAdapter(layoutId: Int, listData: MutableList<ScheduleBean>?) :
    BaseQuickAdapter<ScheduleBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: ScheduleBean) {
        viewHolder?.let { holder ->
            var addressStr = ""
            var participantStr = ""
            var remarkStr = "" + item.remark

            holder.setText(R.id.tvTitle5, item?.title)
                .setText(R.id.tvTime5, item?.scheduletime)
                .setText(R.id.tvAddress5, addressStr)
                .setText(R.id.tvParticipant5, participantStr)
                .setText(R.id.tvRemark5, remarkStr)

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