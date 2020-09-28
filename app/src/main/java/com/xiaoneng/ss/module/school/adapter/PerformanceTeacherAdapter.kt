package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.PerformanceBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class PerformanceTeacherAdapter(layoutId: Int, listData: MutableList<PerformanceBean>) :
    BaseQuickAdapter<PerformanceBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: PerformanceBean?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvTime3Performance, DateUtil.formatShowTime(item?.testtime?:""))
                .setText(R.id.tvRank3Performance,item?.ranking)
                .setText(R.id.tvCno3Performance,item?.cno)
                .setText(R.id.tvName3Performance,item?.realname)
            .setText(R.id.tvScore3Performance, item?.achievement)
        }
    }
}