package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.TimetableLabelBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class TimetableLabelAdapter(layoutId: Int, listData: MutableList<TimetableLabelBean>) :
    BaseQuickAdapter<TimetableLabelBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: TimetableLabelBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvLabelTimetableA, item.s)
                .setText(R.id.tvLabelTimetableB, item.e)
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)

        }
    }

}