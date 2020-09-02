package com.xiaoneng.ss.module.circular.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.circular.model.ScheduleBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class DaysOfMonthAdapter(layoutId: Int, listData: MutableList<DayBean>?) :
    BaseQuickAdapter<DayBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: DayBean) {
        viewHolder?.let { holder ->
            var eventData = ArrayList<ScheduleBean>()
            if (item.eventList.size > 2) {
                eventData.add(item.eventList[0])
                eventData.add(item.eventList[1])
                eventData.add(ScheduleBean(title = "···", color = "#5E37FF"))
            } else {
                eventData.addAll(item.eventList)
            }
            var recyclerView = holder.getView<RecyclerView>(R.id.rvEventDay).apply {
                visibility = View.VISIBLE
                layoutManager = LinearLayoutManager(mContext)
                adapter = EventSimpleAdapter(R.layout.item_event_simple, eventData)
            }
            holder
                .setText(R.id.tvDay2, item?.dayOfSun)
                .setText(R.id.tvDay3, item?.dayOfLunar)
            if (item?.isCheck!!) {
                holder.setBackgroundRes(R.id.tvDay2, R.drawable.bac_blue_oval)
                holder.setTextColor(R.id.tvDay2, mContext.resources.getColor(R.color.white))
            } else {
                holder.setBackgroundRes(R.id.tvDay2, 0)
                holder.setTextColor(R.id.tvDay2, mContext.resources.getColor(R.color.titleBlack))
            }
        }
    }
}