package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.UserBeanSimple


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InvolveSimpleAdapter(layoutId: Int, listData: MutableList<UserBeanSimple>) :
    BaseQuickAdapter<UserBeanSimple, BaseViewHolder>(layoutId, listData) {

    private var max: Int = 0

    override fun convert(viewHolder: BaseViewHolder, item: UserBeanSimple) {
        viewHolder?.let { holder ->

            if (holder.adapterPosition > max-2) {
                holder.setText(R.id.tvNameInvolve, "其他参与者")
                    .setText(R.id.tvLabelInvolve, "")
                holder.getView<View>(R.id.tvLabelInvolve).setBackgroundResource(R.drawable.ic_other)
            } else {
                item.realname?.let {

                    holder.setText(R.id.tvNameInvolve, it)
                    if (it.isNotEmpty()) {
                        holder.setText(R.id.tvLabelInvolve, item.realname!!.last().toString())
                    }
                }
                holder.getView<View>(R.id.tvLabelInvolve)
                    .setBackgroundResource(R.drawable.bac_blue_oval)
            }
//            if (DateUtil.isSameDay(item.time.toLong() * 1000)) {
//                holder.setTextColor(R.id.tvNameWeek,mContext.resources.getColor(R.color.themeColor))
//            } else {
//                holder.setTextColor(R.id.tvNameWeek,mContext.resources.getColor(R.color.commonHint))
//            }

        }
    }

    fun setMax(i: Int) {
        max = i
    }

}