package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.module.school.model.PerformanceBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class PerformanceStuAdapter(layoutId: Int, listData: MutableList<PerformanceBean>?) :
    BaseQuickAdapter<PerformanceBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: PerformanceBean?) {
        viewHolder?.let { holder ->
//            holder.setText(R.id.tvTitle3, item?.title)
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}