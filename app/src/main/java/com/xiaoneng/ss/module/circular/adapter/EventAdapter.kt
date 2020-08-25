package com.xiaoneng.ss.module.circular.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.module.circular.model.DayBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class EventAdapter(layoutId: Int, listData: MutableList<DayBean>?) :
    BaseQuickAdapter<DayBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: DayBean?) {
        viewHolder?.let { holder ->
var addressStr = "地点"
var participantStr = ""

//            holder.setText(R.id.tvDay1, item?.dayOfWeek)
//                .setText(R.id.tvDay2,item?.dayOfSun)
//                .setText(R.id.tvDay3,item?.dayOfLunar)
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